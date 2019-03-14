package com.demo.concurrencyinpractice.problem.d_task;

import org.world.util.ExceptionUtil;

import java.util.List;
import java.util.concurrent.*;

/**
 * 使用 CompletionService,使页面元素在下载完成后立即显示出来
 * <p>
 *     通过 CompletionService 从两个方面来提高页面渲染器的性能：缩短总运行时间以及提高响应性。
 *     为每一幅图像的下载都创建一个独立任务，并在线程池中执行它们，从而将串行的下载过程转换为并行的过程：
 *     这将减少下载所有图像的总时间。此外，通过从 CompletionService 中获取结果以及使没长图片在下载完成后立刻显示出来，
 *     能使用户获取一个更加动态和更高响应性的用户页面。
 * </p>
 * <p>
 *     多个 ExecutorCompletionService 可以共享一个 Executor ，因此可以创建一个对于特定计算私有，
 *     又能共享一个公共 Executor 的 ExecutorCompletionService。因此，CompletionService 的作用就相当于一组计算的句柄，
 *     这与 Future 作为单个计算的句柄是非常类似的。通过记录提交给 CompletionService 的任务数量，
 *     并计算出已经获得的已完成结果的数量，即使使用一个共享的 Executor，也能知道已经获得了所有任务结果的时间。
 * </p>
 */
public abstract class Renderer {
    private final ExecutorService executor;

    Renderer(ExecutorService executor){
        this.executor = executor;
    }

    void renderPage(CharSequence source){
        List<ImageInfo> info = scanForImageInfo(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executor);
        for (final ImageInfo imageInfo: info) {
            completionService.submit(new Callable<ImageData>() {
                @Override
                public ImageData call() throws Exception {
                    return imageInfo.downloadImage();
                }
            });

            renderText(source);
            try{
                for (int i = 0,n = info.size(); i < n; i++) {
                    Future<ImageData> f= completionService.take();
                    ImageData imageData = f.get();
                    renderImage(imageData);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                throw ExceptionUtil.launderThrowable(e.getCause());
            }


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
