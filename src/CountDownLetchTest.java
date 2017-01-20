import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ���絹��ʱ������������CountDownLatch�����countDown�����ͽ���������һ��
 * ������������0ʱ�������еȴ��߻򵥸��ȴ��߿�ʼִ�С�
 * ����ʵ��һ���ˣ�Ҳ�����Ƕ���ˣ��ȴ����������˶���֪ͨ��������ʵ��һ����֪ͨ����˵�Ч����
 * ���Ʋ���һ������˶�Աͬʱ��ʼ���ܣ����������˶�Ա���ܵ�
 * �յ����вſ��Թ��������������ʵ��һ���ƻ���Ҫ����쵼��ǩ�ֺ�
 * ���ܼ�������ʵʩ�����
 * @author Administrator
 *
 */
public class CountDownLetchTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
        
        
        final CountDownLatch cdOrder = new CountDownLatch(1); //��������ʼֵ 1
        final CountDownLatch cdAnswer = new CountDownLatch(3);
        for(int i = 0;i<3;i++){
            Runnable runnable = new Runnable() {
                
                @Override
                public void run() {
                    try {
                        System.out.println("�߳�"+Thread.currentThread().getName()
                                +"��׼����������");
                        cdOrder.await();
                        System.out.println("�߳�"+Thread.currentThread().getName()
                                +"�ѽ�������");
                        Thread.sleep((long)(Math.random()*10000));
                        System.out.println("�߳�"+Thread.currentThread().getName()
                                +"��Ӧ�������");
                        cdAnswer.countDown();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            };
            executorService.execute(runnable);
        }
        try {
            Thread.sleep((long)(Math.random()*10000));
            System.out.println("�߳�"+Thread.currentThread().getName()
                    +"������������");
            cdOrder.countDown();  //��������ֵ�� 1
            System.out.println("�߳�"+Thread.currentThread().getName()
                    +"�ѷ���������ڵȴ����");
            cdAnswer.await();
            
            
            System.out.println("�߳�"+Thread.currentThread().getName()
                    +"���յ�������Ӧ���");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}