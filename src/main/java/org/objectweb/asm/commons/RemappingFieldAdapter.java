package org.objectweb.asm.commons;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.TypePath;

/**
 * @author hp888 on 13.06.2019.
 */

public class RemappingFieldAdapter extends FieldVisitor {
    private final Remapper remapper;

    public RemappingFieldAdapter(FieldVisitor fieldVisitor, Remapper remapper) {
        this(393216, fieldVisitor, remapper);
    }

    protected RemappingFieldAdapter(int api, FieldVisitor fieldVisitor, Remapper remapper) {
        super(api, fieldVisitor);
        this.remapper = remapper;
    }

    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = this.fv.visitAnnotation(this.remapper.mapDesc(descriptor), visible);
        return annotationVisitor == null ? null : new RemappingAnnotationAdapter(annotationVisitor, this.remapper);
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitTypeAnnotation(typeRef, typePath, this.remapper.mapDesc(descriptor), visible);
        return annotationVisitor == null ? null : new RemappingAnnotationAdapter(annotationVisitor, this.remapper);
    }
}
