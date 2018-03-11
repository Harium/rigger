package com.harium.rigger.examples;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Bone {

    private String name;
    private float length = 1;
    private Quaternion quaternion;

    private List<Bone> children;

    public Bone(String name) {
        this();
        this.name = name;
    }

    public Bone() {
        quaternion = new Quaternion();
        children = new ArrayList<>();
    }

    public void attach(Bone bone) {
        children.add(bone);
    }

    public void apply(Vector3 vertex) {
        quaternion.transform(vertex);
    }

    public void apply(Vector3 vertex, float weight) {
        Quaternion weightQuaternion = weightQuaternion(weight);
        weightQuaternion.transform(vertex);
    }

    private Quaternion weightQuaternion(float weight) {
        // Don't worry, JVM takes care of polling
        Quaternion weightQuaternion = new Quaternion();
        weightQuaternion.slerp(quaternion, weight);
        return weightQuaternion;
    }

    public void setEulerAngles(float yaw, float pitch, float roll) {
        quaternion.setEulerAngles(yaw, pitch, roll);
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public List<Bone> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
