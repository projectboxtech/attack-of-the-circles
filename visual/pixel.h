#ifndef PIXEL_H
#define PIXEL_H

#include "../math/Vector3D.h"

template <typename T>
class Pixel: protected Vector3D<T> {
	public:
	T &r=Vector3D<T>::x_, &g=Vector3D<T>::y_, &b=Vector3D<T>::z_, a=0;
};

#endif