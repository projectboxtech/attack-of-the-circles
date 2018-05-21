#ifndef VECTOR3D_H
#define VECTOR3D_H

#include <math.h>
#include "../visual/Pixel.h"

template <typename T> 
class Pixel;

template <typename T>
class Vector3D
{
	friend class Pixel<T>;
	protected:
		T x_, y_, z_;

	public:
		Vector3D() : x_(0),y_(0),z_(0) {}
		Vector3D(T x, T y, T z) : x_(x), y_(y), z_(z) {}
		
		/**
		 * Operators 
		 */
		Vector3D operator +(const T rhs) {
			return Vector3D(x_ + rhs, y_ + rhs, z_ + rhs);
		}
		
		Vector3D operator -(const T rhs) {
			return Vector3D(x_ - rhs, y_ - rhs, z_ - rhs);
		}

		Vector3D operator -(const Vector3D& rhs) {
			return Vector3D(x_ - rhs.x_, y_ - rhs.y_, z_ - rhs.z_);
		}
		
		Vector3D operator /(const T rhs) {
			return Vector3D(x_ / rhs, y_ / rhs, z_ / rhs);
		}
		
		Vector3D operator *(const T rhs) {
			return Vector3D(x_ * rhs, y_ * rhs, z_ * rhs);
		}
		
		Vector3D operator *(const Vector3D& rhs) {
			return Vector3D(x_ * rhs.x_, y_ * rhs.y_, z_ * rhs.z_);
		}
		
		T dot (const Vector3D& rhs) {
			return rhs.x_ * x_ + rhs.y_ * y_ + rhs.z_ * z_;
		}
		
		Vector3D cross (const Vector3D& rhs) {
			return Vector3D(rhs.z_ * y_ - rhs.y_ * z_, rhs.x_ * z_ - rhs.z_ * x_, rhs.y_ * x_ - rhs.x_ * y_);
		}
		
		T mag () {
			return sqrt(x_*x_+y_*y_+z_*z_);
		}
		
		Vector3D unit () {
			return (*this) / mag();
		}
		
		/**
		 * Assignment Operators
		 */ 
		Vector3D& operator +=(const Vector3D& rhs) {
			x_ += rhs.x_;
			y_ += rhs.y_;
			z_ += rhs.z_;
			return *this;
		}
		
		Vector3D& operator +=(const T v) {
			x_ += v;
			y_ += v;
			z_ += v;
			return *this;
		}
		
		Vector3D& operator -=(const Vector3D& rhs) {
			x_ -= rhs.x_;
			y_ -= rhs.y_;
			z_ -= rhs.z_;
			return *this;
		}
		
		Vector3D& operator *=(const T v) {
			x_ *= v;
			y_ *= v;
			z_ *= v;
			return *this;
		}
		
		/**
		 * Getters
		 */
		T getX () const { return x_; }
		T getY () const { return y_; }
		T getZ () const { return z_; }
		
		/**
		 * Setters
		 */
		void setX (T x) {x_=x;}
		void setY (T y) {y_=y;}
		void setZ (T z) {z_=z;}
};

#endif