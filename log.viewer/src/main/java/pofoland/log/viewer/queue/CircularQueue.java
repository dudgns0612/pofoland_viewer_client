package pofoland.log.viewer.queue;

import pofoland.log.viewer.utils.LoggerManager;

public class CircularQueue {
	
	private int arraySize = 10;
	private int front = 0; 
	private int rear = 0;
	private int count = 0;
	Object[] arr = null;
	
	//생성자 기본
	public CircularQueue() {
		arr = new Object[arraySize];
	}
	
	//생성자
	public CircularQueue(int arraySize) {
		this.arraySize = arraySize;
		arr = new Object[arraySize];
	}

	public void enqueue(Object data) {
		if ((rear + 1) % arraySize == front % arraySize) {
			LoggerManager.info(getClass(), "FULL QUEUE SIZE");
			dequeue();
			enqueue(data);
		} else {
			rear = (rear + 1) % arraySize;
			arr[rear] = data;
			count = rear+1;
		}
	}

	public void dequeue() {
		if (front == rear) {
			LoggerManager.info(getClass(), "EMPTY QUEUE SIZE");
		} else {
			front = (front + 1) % arraySize;
			arr[front] = null;
		}
	}

	public Object getFrontItem() {
		return arr[front + 1];
	}
	
	public Object getQueueItem(int index) {
		return arr[index];
	}
	
	public int getFront() {
		return front;
	}
	
	public int rear() {
		return rear;
	}
	
	public String getCountData(int keyCode) {
		String countData = "";

		if (keyCode == 38) {
			count--;
			if (count < 0) {
				count = rear;
			}
		} else {
			count++;
			if (count > rear) {
				count = front;
			}
		}
		countData = String.valueOf(arr[count]);
		
		if (countData == "null") {
			countData = "";
		}
		
		return countData;
	}
}
