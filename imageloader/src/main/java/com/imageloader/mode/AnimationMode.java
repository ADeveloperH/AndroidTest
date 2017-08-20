package com.imageloader.mode;

/**
 * author：hj
 * time: 2017/6/11 0011 21:45
 * 加载动画的模式
 */


public enum AnimationMode {
    //动画以资源id形式
    ANIMATIONID,
    //动画为Animation形式
    ANIMATION,
    //动画以ViewPropertyAnimation.Animator形式
    ANIMATOR
}
