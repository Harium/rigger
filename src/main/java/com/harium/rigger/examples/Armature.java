package com.harium.rigger.examples;

import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Map;

public class Armature {

    private static final float WEIGHT_DEFAULT = 1;

    private Vector3 position;
    private Map<Bone, Map<Vector3, Float>> weights;


    public Armature() {
        position = new Vector3();
        weights = new HashMap<>();
    }

    public Armature(float x, float y, float z) {
        position = new Vector3(x, y, z);
        weights = new HashMap<>();
    }

    public Armature(Vector3 position) {
        this.position = position;
        weights = new HashMap<>();
    }

    public void addBone(Bone bone) {
        weights.put(bone, new HashMap<Vector3, Float>());
    }

    public void associate(Bone bone, Vector3 vertex) {
        associate(bone, vertex, WEIGHT_DEFAULT);
    }

    public void associate(Bone bone, Vector3 vertex, float weight) {
        Map<Vector3, Float> map = weights.get(bone);
        map.put(vertex, weight);
    }

    public void update() {
        for (Bone bone : weights.keySet()) {
            updateBone(bone);
        }
    }

    private void updateBone(Bone bone) {
        Map<Vector3, Float> list = weights.get(bone);
        // Apply rotation to own vertices
        for (Map.Entry<Vector3, Float> entry : list.entrySet()) {
            Vector3 vertex = entry.getKey();
            float weight = entry.getValue();
            bone.apply(vertex, weight);
        }
        // Apply rotation to children
        for (Bone child : bone.getChildren()) {
            updateBone(bone, child);
        }
    }

    private void updateBone(Bone bone, Bone child) {
        Map<Vector3, Float> list = weights.get(child);
        for (Map.Entry<Vector3, Float> entry : list.entrySet()) {
            Vector3 vertex = entry.getKey();
            float weight = entry.getValue();
            bone.apply(vertex, weight);
        }
        // Apply the rotation to children
        for (Bone grandchild : child.getChildren()) {
            updateBone(bone, grandchild);
        }
    }

    public void reset() {
        for (Bone bone : weights.keySet()) {
            resetBone(bone);
        }
    }

    private void resetBone(Bone bone) {
        Map<Vector3, Float> list = weights.get(bone);
        // Apply rotation to own vertices
        for (Map.Entry<Vector3, Float> entry : list.entrySet()) {
            Vector3 vertex = entry.getKey();
            float weight = entry.getValue();
            bone.reset(vertex, weight);
        }
        // Apply rotation to children
        for (Bone child : bone.getChildren()) {
            resetBone(bone, child);
        }
    }

    private void resetBone(Bone bone, Bone child) {
        Map<Vector3, Float> list = weights.get(child);
        for (Map.Entry<Vector3, Float> entry : list.entrySet()) {
            Vector3 vertex = entry.getKey();
            float weight = entry.getValue();
            bone.reset(vertex, weight);
        }
        // Apply the rotation to children
        for (Bone grandchild : child.getChildren()) {
            resetBone(bone, grandchild);
        }
    }
}
