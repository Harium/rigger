package com.harium.rigger.examples;

import com.badlogic.gdx.math.Vector3;
import org.junit.Assert;
import org.junit.Test;

public class BoneTest {

    private static final float EPSILON = 0.1f;

    @Test
    public void testBoneRotation() {
        Armature armature = new Armature();

        Bone bone = new Bone();
        armature.addBone(bone);

        Vector3 vertex = new Vector3(1, 0, 0);
        armature.associate(bone, vertex);

        bone.setEulerAngles(-90, 0, 0);
        armature.update();

        Assert.assertEquals(0, vertex.x, EPSILON);
        Assert.assertEquals(0, vertex.y, EPSILON);
        Assert.assertEquals(1, vertex.z, EPSILON);
    }

    @Test
    public void testBoneAffectingChildVertex() {
        Armature armature = new Armature();

        Bone bone = new Bone("Root");
        armature.addBone(bone);

        Bone child = new Bone("Child");
        bone.attach(child);
        armature.addBone(child);

        Vector3 vertex = new Vector3(1, 0, 0);
        armature.associate(child, vertex);

        bone.setEulerAngles(-90, 0, 0);
        armature.update();

        Assert.assertEquals(0, vertex.x, EPSILON);
        Assert.assertEquals(0, vertex.y, EPSILON);
        Assert.assertEquals(1, vertex.z, EPSILON);
    }

}
