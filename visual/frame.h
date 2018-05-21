#ifndef FRAME_H
#define FRAME_H

#include <memory>
#include "pixel.h"

template <typename T, size_t H, size_t W>
class Frame {
    public:
    static const size_t height = H;
    static const size_t width = W;
    static const size_t size = H * W;
    Pixel<T> pixels[H*W];
};

#endif