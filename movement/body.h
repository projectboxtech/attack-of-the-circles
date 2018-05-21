#ifndef BODY_H
#define BODY_H

#include "../math/Vector3D.h"

class Body {
    private:
    Vector3D position;
    Vector3D velocity;

    public:
    Body(Vector3D _position, Vector3D _velocity) : position(_position), velocity(_velocity) {}

    void advance(double dt){
        position += velocity * dt;
    }

    void advance(double dt, Vector3D& acceleration){
        velocity += acceleration * dt;
        position += velocity * dt;
    }
};

#endif