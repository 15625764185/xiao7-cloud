package com.atguigu.state.money;

/**������*/
public class ClientTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//����context ����
		Context context = new Context();
		//����ǰ״̬����Ϊ PublishState
		//context.setState(new PublishState());
		System.out.println(context.getCurrentState());
		context.checkEvent(context);
		context.makePriceEvent(context);
//        //publish --> not pay
		context.acceptOrderEvent(context);
//        //not pay --> paid
		context.payOrderEvent(context);
//        // ʧ��, ���ʧ��ʱ�����׳��쳣
//        try {
//        	context.checkFailEvent(context);
//        	System.out.println("��������..");
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println(e.getMessage());
//		}

	}

}
