package io.ticticboom.mods.mm.log;

import io.ticticboom.mods.mm.Ref;

import java.util.Stack;

public class LogContextStack {

    private Stack<ILogContextElement> stack = new Stack<>();

    public void push(ILogContextElement ctx) {
        stack.push(ctx);
    }

    public void push(String ctx) {
        push(new StringLogContextElement(ctx));
    }

    public ILogContextElement pop() {
        return stack.pop();
    }

    public void doThrow(Exception e) {
        Ref.LOG.warn("START: ERROR CONTEXT STACK");
        for (ILogContextElement elem : stack) {
            String format = elem.format();
            Ref.LOG.warn("|- {}", format);
        }
        Ref.LOG.warn("END: ERROR CONTEXT STACK");
        throw new RuntimeException(e);
    }

    public void reset(ILogContextElement start) {
        stack.clear();
        stack.push(start);
    }

    public void reset(String start) {
        reset(new StringLogContextElement(start));
    }
}
