package hw07.asm.instrumentation;

import hw07.asm.annotations.Log;
import org.objectweb.asm.*;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

class MethodNameAndArgumentsPrinterAdapter extends MethodVisitor {

    private final String name;
    private final String descriptor;
    private boolean annotationPresent;

    protected MethodNameAndArgumentsPrinterAdapter(int api, MethodVisitor methodVisitor, String name, String descriptor) {
        super(api, methodVisitor);
        this.name = name;
        this.descriptor = descriptor;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        annotationPresent = Type.getDescriptor(Log.class).equals(descriptor);

        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public void visitCode() {

        if (annotationPresent) {
            super.visitFieldInsn(
                    Opcodes.GETSTATIC,
                    "java/lang/System",
                    "out",
                    "Ljava/io/PrintStream;");

            var dynamicDescriptor = new StringBuilder();
            dynamicDescriptor.append("(");

            var bootstrapMethodArguments = new StringBuilder();
            bootstrapMethodArguments.append("executed method: " + name);

            var arguments = Type.getArgumentTypes(descriptor);
            for (var i = 0; i < arguments.length; i++) {
                super.visitVarInsn(arguments[i].getOpcode(Opcodes.ILOAD), i + 1);
                dynamicDescriptor.append(arguments[i].getDescriptor());
                if (bootstrapMethodArguments.length() != 0) {
                    bootstrapMethodArguments.append(", ");
                }
                bootstrapMethodArguments.append("param" + (i + 1) + ": \u0001");
            }
            dynamicDescriptor.append(")Ljava/lang/String;");

            Handle handle = new Handle(
                    Opcodes.H_INVOKESTATIC,
                    "java/lang/invoke/StringConcatFactory",
                    "makeConcatWithConstants",
                    MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                    false);

            super.visitInvokeDynamicInsn(
                    "makeConcatWithConstants",
                    dynamicDescriptor.toString(),
                    handle,
                    bootstrapMethodArguments.toString());

            super.visitMethodInsn(
                    Opcodes.INVOKEVIRTUAL,
                    "java/io/PrintStream",
                    "println",
                    "(Ljava/lang/String;)V",
                    false);
        }

        super.visitCode();
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack, maxLocals);
    }
}
