package org.objectweb.asm.commons;

import org.objectweb.asm.AnnotationVisitor;

/**
 * @author hp888 on 13.06.2019.
 */

public class RemappingAnnotationAdapter extends AnnotationVisitor {
    protected final Remapper remapper;

    public RemappingAnnotationAdapter(AnnotationVisitor annotationVisitor, Remapper remapper) {
        this(393216, annotationVisitor, remapper);
    }

    protected RemappingAnnotationAdapter(int api, AnnotationVisitor annotationVisitor, Remapper remapper) {
        super(api, annotationVisitor);
        this.remapper = remapper;
    }

    public void visit(String name, Object value) {
        this.av.visit(name, this.remapper.mapValue(value));
    }

    public void visitEnum(String name, String descriptor, String value) {
        this.av.visitEnum(name, this.remapper.mapDesc(descriptor), value);
    }

    public AnnotationVisitor visitAnnotation(String name, String descriptor) {
        AnnotationVisitor annotationVisitor = this.av.visitAnnotation(name, this.remapper.mapDesc(descriptor));
        return annotationVisitor == null ? null : (annotationVisitor == this.av ? this : new RemappingAnnotationAdapter(annotationVisitor, this.remapper));
    }

    public AnnotationVisitor visitArray(String name) {
        AnnotationVisitor annotationVisitor = this.av.visitArray(name);
        return annotationVisitor == null ? null : (annotationVisitor == this.av ? this : new RemappingAnnotationAdapter(annotationVisitor, this.remapper));
    }
}
