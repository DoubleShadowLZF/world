package com.demo.concurrencyinpractice.problem.d_task;

import java.util.ArrayList;
import java.util.List;

/**
 * 串行地渲染页面元素
 * <p>
 *     最简单的方法就是对HTML文档进行串行处理。当遇到文本标签时，将其绘制到图像缓存中。
 *     当遇到图像引用时，先通过网络获取它，然后再将其回执到图像缓存中。这很容易实现，
 *     程序只需将输入中的每个元素处理一次（甚至不需要缓存文档），
 *     但这种方法可能会令用户干到烦恼，他们必须要等待很长时间，知道现实所有文本。
 * </p>
 * <p>
 *     另一种串行执行方法更好一些，它先绘制文本元素，同时为图像预留出矩形的占位空间，
 *     在处理完了第一遍文本后，程序再开始下载图像，并将它们绘制到相应的占位空间中。
 *     在程序 SingleThreadRenderer 中给出这种方法。
 * </p>
 * <p>
 *     缺点：
 *     图像下载过程的大部分时间都是等待I/O操作执行完成，在这期间CPU几乎不做任何工作。
 *     因此，这种串行执行方法没有充分地利用CPU，使得用户在看到最终页面之前要等待过长的时间。
 *     通过将问题分解为多个独立的任务并行执行，能够获得更高的CPU利用率和响应灵敏度。
 * </p>
 */
public abstract class SingleThreadRenderer {
    void renderPage(CharSequence source){
        renderText(source);
        List<ImageData> imageData = new ArrayList<ImageData>();
        for(ImageInfo imageInfo : scanForImageInfo(source)){
            imageData.add(imageInfo.downloadImage());
        }
        for(ImageData data : imageData){
            renderImage(data);
        }
    }

    interface ImageData{}

    interface ImageInfo{
        ImageData downloadImage();
    }

    abstract void renderText(CharSequence source);
    abstract List<ImageInfo> scanForImageInfo(CharSequence source);
    abstract void renderImage(ImageData data);
}
