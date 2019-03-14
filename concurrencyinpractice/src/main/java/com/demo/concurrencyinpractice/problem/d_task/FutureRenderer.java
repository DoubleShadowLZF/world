package com.demo.concurrencyinpractice.problem.d_task;

import org.world.util.ExceptionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 使用 Future 等待图像下载
 * <p>
 *     为了是页面渲染器实现更高的并发性，首先将渲染过程分为两个任务，一个是渲染所有的文本，
 * 另一个是下载所有的图像。（因为其中一个是任务CPU密集型，而另一个是I/O密集型，
 * 因此，这种方法即使在单个CPU系统上也能提升性能。）
 * </p>
 * <p>
 *     Callable 和 Future 有助于表示这个协同任务之间的交互。在程序 FutureRenderer 中创建一个 Callable 来
 *     下载所有的图像，并将其提交到一个 ExecutorService 。这将返回一个描述任务执行情况的Future。当主任务需要图像时，
 *     它会等待Future.get的调用结果。如果幸运的话，当开始请求时，所有图像就已经下载完成了，即使没有，至少图像的下载任务也已经提前开始了。
 * </p>
 * <p>
 *     get 方法拥有“状态依赖”的内在特性，因而调用者不需要知道任务的状态，此外在任务提交和获得结果中包含的安全发布属性也确保了这个方法是线程安全的。
 *     Future.get的异常处理代码将处理两个可能的问题：任务遇到一个Exception，或者调用get的线程在获得结果之前被中断。
 * </p>
 * <p>
 *     FutureRenderer 使得渲染任务与下载图像数据的任务并发地执行。当所有图像下载完后，会显示到页面上。这将提升用户体验，
 *     不仅使用户更快地看到结果，还有效利用了并行性，但我们还可以做得更好。用户不必等到所有的图像都下载完成，
 *     而希望看到每当下载完幅图像时就立即显示出来。
 * </p>
 */
public abstract class FutureRenderer {
    private final ExecutorService executor = Executors.newCachedThreadPool();

    void renderPage(CharSequence source){
        final List<ImageInfo> imageInfos = scanForImageInfo(source);
        Callable<List<ImageData>> task = new Callable<List<ImageData>>() {
            @Override
            public List<ImageData> call() throws Exception {
                List<ImageData> result = new ArrayList<>();
                for(ImageInfo imageInfo : imageInfos)
                    result.add(imageInfo.downloadImage());
                return result;
            }
        };

        Future<List<ImageData>> future = executor.submit(task);
        renderText(source);
        try{
            List<ImageData> imageData = future.get();
            for (ImageData data : imageData) {
                renderImage(data);
            }
        } catch (InterruptedException e) {
            //重新设置线程的中断状态
            Thread.currentThread().interrupt();
            //由于不需要结果，因此取消结果
            future.cancel(true);
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw ExceptionUtil.launderThrowable(e.getCause());
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
