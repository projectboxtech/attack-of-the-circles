#ifndef FRAME_RECORDER_H
#define FRAME_RECORDER_H

#include <memory>
#include <vector>
#include <math.h>
#include "frame.h"

template <typename T, size_t H, size_t W>
class FrameRecorder {
    private: 
    std::vector<std::shared_ptr<Frame<T, H, W>>> frames;
    long n_frames = 0; // the number of frames inserted
    long n_frames_flushed = 0; // the number of frames flushed

    inline double clamp(double x){ return x<0 ? 0 : x>1 ? 1 : x; } 
    inline int toInt(double x){ return round(x); } 

    void flush() 
    {
        if(n_frames_flushed == n_frames)
            return;
        printf("flushing\n");
        for(auto const& frame: frames)
        {
            FILE *f = fopen((std::string("out/image") + std::to_string(n_frames_flushed) + ".ppm").c_str(), "w"); // Write image to PPM file. 
            fprintf(f, "P3\n%d %d\n%d\n", W, H, 255); // 32 bit RGB (16,777,216 colors with alpha)
            for (size_t i_pixel = 0 ; i_pixel < W*H; i_pixel++) 
                fprintf(f,"%d %d %d ", 
                toInt(frame->pixels[i_pixel].r*255.0), 
                toInt(frame->pixels[i_pixel].g*255.0), 
                toInt(frame->pixels[i_pixel].b*255.0));
            fclose (f);
            n_frames_flushed++;
        }
    }

    public:
    static const size_t height = H;
    static const size_t width = W;
    static const size_t size = H * W;
    static const size_t flush_limit = 100;

    void insertFrame (std::shared_ptr<Frame<T, H, W>> frame) {
        frames.push_back(frame);
        n_frames++;
        if((n_frames - n_frames_flushed) >= flush_limit ) {
            flush();
        }
    }

    ~FrameRecorder(){
        flush();
    }
};

#endif