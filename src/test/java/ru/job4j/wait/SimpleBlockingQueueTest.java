package ru.job4j.wait;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {


    @Test
    void WhenTwoConsumersPoll() throws InterruptedException {
        SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>(7);
        Thread producer =new Thread(
                () -> {
                    for (int i = 0; i < 7; i++) {
                        try {
                            queue.offer("Message " + String.valueOf(i));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Thread consumer =new Thread(
                () -> {
                    try {
                        queue.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );

        Thread consumer2 =new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 3; i++) {
                            queue.poll();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );

        producer.start();
        consumer.start();
        consumer2.start();
        producer.join();
        consumer.join();
        consumer2.join();
        assertThat(queue.getSize()).isEqualTo(3);
    }


}