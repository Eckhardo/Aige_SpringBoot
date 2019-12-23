package com.eki.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * What do you think the result of the above program will be? Will the final
 * count be 1000 because we’re calling increment 1000 times?
 * 
 * Well, the answer is no! Just run the above program and see the output for
 * yourself. Instead of producing the final count of 1000, it gives inconsistent
 * result each time it is run. I ran the above program three times on my
 * computer, and the output was 992, 996 and 993.
 * 
 * Let’s dig deeper into the program and understand why the program’s output is
 * inconsistent -
 * 
 * When a thread executes the increment() method, following three steps are
 * performed : 1. Retrieve the current value of count 2. Increment the retrieved
 * value by 1 3. Store the incremented value back in count
 * 
 * Now let’s assume that two threads - ThreadA and ThreadB, execute these
 * operations in the following order -
 * 
 * ThreadA : Retrieve count, initial value = 0 ThreadB : Retrieve count, initial
 * value = 0 ThreadA : Increment retrieved value, result = 1 ThreadB : Increment
 * retrieved value, result = 1 ThreadA : Store the incremented value, count is
 * now 1 ThreadB : Store the incremented value, count is now 1
 * 
 * Both the threads try to increment the count by one, but the final result is 1
 * instead of 2 because the operations executed by the threads interleave with
 * each other. In the above case, the update done by ThreadA is lost.
 * 
 * The above order of execution is just one possibility. There can be many such
 * orders in which these operations can execute making the program’s output
 * inconsistent.
 * 
 * When multiple threads try to read and write a shared variable concurrently,
 * and these read and write operations overlap in execution, then the final
 * outcome depends on the order in which the reads and writes take place, which
 * is unpredictable. This phenomenon is called Race condition.
 * 
 * The section of the code where a shared variable is accessed is called
 * Critical Section.
 * 
 * Thread interference errors can be avoided by synchronizing access to shared
 * variables. We’ll learn about synchronization in the next section.
 * 
  * 
 * @author eckha
 *
 */
public class RaceConditionExample {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(2);

		Counter counter = new Counter();

		for (int i = 0; i < 1000; i++) {
			executorService.submit(() -> counter.increment());
		}

		executorService.shutdown();
		executorService.awaitTermination(60, TimeUnit.SECONDS);

		System.out.println("Final count is : " + counter.getCount());
	}
}
