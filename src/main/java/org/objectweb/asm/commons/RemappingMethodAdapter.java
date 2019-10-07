package org.objectweb.asm.commons;

import org.objectweb.asm.*;

/**
 * @author hp888 on 13.06.2019.
 */

public class RemappingMethodAdapter extends LocalVariablesSorter
{
    protected final Remapper remapper;

    public RemappingMethodAdapter(int access, String descriptor, MethodVisitor methodVisitor, Remapper remapper) {
        this(393216, access, descriptor, methodVisitor, remapper);
    }

    protected RemappingMethodAdapter(int api, int access, String descriptor, MethodVisitor methodVisitor, Remapper remapper) {
        super(api, access, descriptor, methodVisitor);
        this.remapper = remapper;
    }

    public AnnotationVisitor visitAnnotationDefault() {
        AnnotationVisitor annotationVisitor = super.visitAnnotationDefault();
        return (annotationVisitor == null ? annotationVisitor : new RemappingAnnotationAdapter(annotationVisitor, this.remapper));
    }

    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(this.remapper.mapDesc(descriptor), visible);
        return (annotationVisitor == null ? annotationVisitor : new RemappingAnnotationAdapter(annotationVisitor, this.remapper));
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitTypeAnnotation(typeRef, typePath, this.remapper.mapDesc(descriptor), visible);
        return (annotationVisitor == null ? annotationVisitor : new RemappingAnnotationAdapter(annotationVisitor, this.remapper));
    }

    public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitParameterAnnotation(parameter, this.remapper.mapDesc(descriptor), visible);
        return (annotationVisitor == null ? annotationVisitor : new RemappingAnnotationAdapter(annotationVisitor, this.remapper));
    }

    public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
        super.visitFrame(type, numLocal, this.remapEntries(numLocal, local), numStack, this.remapEntries(numStack, stack));
    }

    private Object[] remapEntries(int numTypes, Object[] entries) {
        if (entries == null) {
            return entries;
        } else {
            Object[] remappedEntries = null;

            for(int i = 0; i < numTypes; ++i) {
                if (entries[i] instanceof String) {
                    if (remappedEntries == null) {
                        remappedEntries = new Object[numTypes];
                        System.arraycopy(entries, 0, remappedEntries, 0, numTypes);
                    }

                    remappedEntries[i] = this.remapper.mapType((String)entries[i]);
                }
            }

            return remappedEntries == null ? entries : remappedEntries;
        }
    }

    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        super.visitFieldInsn(opcode, this.remapper.mapType(owner), this.remapper.mapFieldName(owner, name, descriptor), this.remapper.mapDesc(descriptor));
    }

    /** @deprecated */
    @Deprecated
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor) {
        if (this.api >= 327680) {
            super.visitMethodInsn(opcode, owner, name, descriptor);
        } else {
            this.doVisitMethodInsn(opcode, owner, name, descriptor, opcode == 185);
        }
    }

    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        if (this.api < 327680) {
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        } else {
            this.doVisitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
    }

    private void doVisitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        if (this.mv != null) {
            this.mv.visitMethodInsn(opcode, this.remapper.mapType(owner), this.remapper.mapMethodName(owner, name, descriptor), this.remapper.mapMethodDesc(descriptor), isInterface);
        }

    }

    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        for(int i = 0; i < bootstrapMethodArguments.length; ++i) {
            bootstrapMethodArguments[i] = this.remapper.mapValue(bootstrapMethodArguments[i]);
        }

        super.visitInvokeDynamicInsn(this.remapper.mapInvokeDynamicMethodName(name, descriptor), this.remapper.mapMethodDesc(descriptor), (Handle)this.remapper.mapValue(bootstrapMethodHandle), bootstrapMethodArguments);
    }

    public void visitTypeInsn(int opcode, String type) {
        super.visitTypeInsn(opcode, this.remapper.mapType(type));
    }

    public void visitLdcInsn(Object value) {
        super.visitLdcInsn(this.remapper.mapValue(value));
    }

    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        super.visitMultiANewArrayInsn(this.remapper.mapDesc(descriptor), numDimensions);
    }

    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitInsnAnnotation(typeRef, typePath, this.remapper.mapDesc(descriptor), visible);
        return (annotationVisitor == null ? annotationVisitor : new RemappingAnnotationAdapter(annotationVisitor, this.remapper));
    }

    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        super.visitTryCatchBlock(start, end, handler, type == null ? null : this.remapper.mapType(type));
    }

    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitTryCatchAnnotation(typeRef, typePath, this.remapper.mapDesc(descriptor), visible);
        return (annotationVisitor == null ? annotationVisitor : new RemappingAnnotationAdapter(annotationVisitor, this.remapper));
    }

    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
        super.visitLocalVariable(name, this.remapper.mapDesc(descriptor), this.remapper.mapSignature(signature, true), start, end, index);
    }

    public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, this.remapper.mapDesc(descriptor), visible);
        return (annotationVisitor == null ? annotationVisitor : new RemappingAnnotationAdapter(annotationVisitor, this.remapper));
    }
}
