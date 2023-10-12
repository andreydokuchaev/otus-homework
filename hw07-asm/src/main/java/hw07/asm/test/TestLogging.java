package hw07.asm.test;

import hw07.asm.annotations.Log;

public class TestLogging {

    @Log
    public void calculation() {}

    @Log
    public void calculation(int param1) {}

    @Log
    public void calculation(int param1, int param2) {}

    @Log
    public void calculation(int param1, int[] param2, String param3) {}
}
