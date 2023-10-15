package hw07.asm;

import hw07.asm.test.TestLogging;
import hw07.asm.instrumentation.CustomClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;

//run with gradle task runWithAgent
public class Main {

    public static void main(String[] args) throws Exception {
        TestLogging testLogging = new TestLogging();
        testLogging.calculation();
        testLogging.calculation(1);
        testLogging.calculation(1, 2);
        testLogging.calculation(1, new int[] {1, 2}, "test string");
    }
}