package com.harium.rigger.examples;

import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Armature {

    private Vector3 position;
    private Map<Bone, List<Vector3>> associations;

    public Armature() {
        position = new Vector3();
        associations = new HashMap<>();
    }

    public Armature(float x, float y, float z) {
        position = new Vector3(x, y, z);
        associations = new HashMap<>();
    }

    public Armature(Vector3 position) {
        this.position = position;
        associations = new HashMap<>();
    }

    public void addBone(Bone bone) {
        associations.put(bone, new ArrayList<Vector3>());
    }

    public void associate(Bone bone, Vector3 vertex) {
        List<Vector3> list = associations.get(bone);
        list.add(vertex);
    }

    public void update() {
        for (Bone bone : associations.keySet()) {
            updateBone(bone);
        }
    }

    private void updateBone(Bone bone) {
        List<Vector3> list = associations.get(bone);
        // Apply rotation to own vertices
        for (Vector3 vertex : list) {
            bone.apply(vertex);
        }
        // Apply rotation to children
        for (Bone child : bone.getChildren()) {
            updateBone(bone, child);
        }
    }

    private void updateBone(Bone bone, Bone child) {
        List<Vector3> vertices = associations.get(child);
        for (Vector3 vertex : vertices) {
            bone.apply(vertex);
        }
        // Apply the rotation to children
        for (Bone grandchild : child.getChildren()) {
            updateBone(bone, grandchild);
        }
    }
}
