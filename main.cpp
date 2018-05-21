// main.clj
// Copyright © 2018 Jonathan R. Camenzuli (jrcamenzuli@gmail.com)

#include <iostream>
#include <random>
#include "visual/pixel.h"
#include "visual/frame.h"
#include "visual/frame_recorder.h"

using namespace std;

int main (int argc, char** argv)
{
    cout << "start" << endl;

    const size_t FRAME_HEIGHT = 1000;
    const size_t FRAME_WIDTH = 1000;

    const double lower_bound = 0.0;
    const double upper_bound = 1.0;
    std::uniform_real_distribution<double> unif(lower_bound,upper_bound);
    std::default_random_engine re;
    // unif(re)

    FrameRecorder<double, FRAME_HEIGHT, FRAME_WIDTH> frame_recorder;

    // render 100 frames
    for(size_t i = 0 ; i < 100 ; i++)
    {
        std::shared_ptr<Frame<double, 1000, 1000>> frame(new Frame<double, FRAME_HEIGHT,FRAME_WIDTH>);
        for(size_t i = 0 ; i < frame->size ; i++)
        {
            // frame->pixels[i].r = unif(re);
            // frame->pixels[i].g = unif(re);
            // frame->pixels[i].b = unif(re);
        }
        frame_recorder.insertFrame(frame);
    }

    cout << "stop" << endl;
    return 0;
}