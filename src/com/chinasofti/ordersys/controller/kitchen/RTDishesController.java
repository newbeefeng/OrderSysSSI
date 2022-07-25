package com.chinasofti.ordersys.controller.kitchen;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinasofti.util.web.serverpush.Message;
import com.chinasofti.util.web.serverpush.MessageConsumer;
import com.chinasofti.util.web.serverpush.MessageHandler;
import com.chinasofti.util.web.serverpush.MessageProducer;
import com.chinasofti.util.web.serverpush.ServerPushKey;

@Controller
public class RTDishesController {

	@Autowired
	GetPushMsgTemplate template;

	public GetPushMsgTemplate getTemplate() {
		return template;
	}

	public void setTemplate(GetPushMsgTemplate template) {
		this.template = template;
	}

	public static ArrayList<String> disheses = new ArrayList<String>();
	public static ArrayList<String> kitchens = new ArrayList<String>();

	@RequestMapping("/dishesdone")
	public void dishesDone(HttpServletRequest request,
			HttpServletResponse response) {
		// ������Ӧ�����
		response.setCharacterEncoding("utf-8");
		// ��ȡ��Ʒ��Ӧ������
		String tableId = request.getParameter("tableId");
		// ��ȡ��Ʒ��
		String dishesName = request.getParameter("dishesName");
		// ����ʹ��ajax�ύ�������Ҫת��
		try {
			dishesName = new String(dishesName.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ������Ϣ������
		MessageProducer producer = new MessageProducer();
		// ��ȡ����Ա�ȴ��б�
		ArrayList<String> list = disheses;
		// ��������Ա�ȴ��б�
		for (int i = list.size() - 1; i >= 0; i--) {
			// ��ȡ�ض��ķ���ԱSessionID
			String id = list.get(i);
			// �Ը÷���Ա������Ʒ��ɵȴ����˵���Ϣ
			producer.sendMessage(id, "rtdishes", "����[" + tableId + "]�Ĳ�Ʒ["
					+ dishesName + "]�Ѿ�������ɣ��봫�ˣ�");
			// �ӵȴ��б���ɾ���÷���Ա
			list.remove(id);
		}

	}

	@RequestMapping("/getrtdishes")
	public void getRTDishes(HttpServletRequest request,
			HttpServletResponse response) {

		GetPushMsgHandler handler = new GetPushMsgHandler() {

			@Override
			public MessageHandler getHandler(HttpServletRequest request,
					HttpServletResponse response) {
				// TODO Auto-generated method stub
				// ���������ַ���
				response.setCharacterEncoding("utf-8");
				// TODO Auto-generated method stub
				// ���Դ���ʵʱ��Ϣ
				try {
					// ��ȡ��Կͻ��˵��ı������
					final PrintWriter out = response.getWriter();
					// ������Ϣ������
					MessageHandler handler = new MessageHandler() {
						// ʵʱ��Ϣ����ص�����
						@Override
						public void handle(
								Hashtable<ServerPushKey, Message> messageQueue,
								ServerPushKey key, Message msg) {
							// ����Ϣ���ı�����ֱ�ӷ��͸��ͻ���
							out.print(msg.getMsg());
							// TODO Auto-generated method stub

						}
					};
					// ���ش����õ���Ϣ������
					return handler;
					// ���񴴽���Ϣ������ʱ�������쳣
				} catch (Exception ex) {
					// ����쳣��Ϣ
					ex.printStackTrace();
					// ����쳣��Ϣ
					return null;
				}
			}

			@Override
			public void initService(HttpServletRequest request,
					HttpServletResponse response, HttpSession session) {
				// TODO Auto-generated method stub
				// ����ǰ�Ự���뵽ʵʱ��Ϣ�ȴ��б�
				disheses.add(session.getId());
			}

		};

		template.getMsg(request, response, handler);
	}

	@RequestMapping("/getrtorder")
	public void getRTOrder(HttpServletRequest request,
			HttpServletResponse response) {

		GetPushMsgHandler handler = new GetPushMsgHandler() {

			/**
			 * ��ȡʵʱ��Ϣ�������Ļص�
			 * 
			 * @param request
			 *            �������
			 * @param response
			 *            ��Ӧ����
			 * @return ��Servletʹ�õ�ʵʱ��Ϣ������
			 * */
			@Override
			public MessageHandler getHandler(HttpServletRequest request,
					HttpServletResponse response) {
				// TODO Auto-generated method stub
				// ���������ַ���
				response.setCharacterEncoding("utf-8");
				// ���Դ���ʵʱ��Ϣ
				try {
					// ��ȡ��Կͻ��˵��ı������
					final PrintWriter out = response.getWriter();
					// ������Ϣ������
					MessageHandler handler = new MessageHandler() {

						@Override
						public void handle(
								Hashtable<ServerPushKey, Message> messageQueue,
								ServerPushKey key, Message msg) {
							// ����Ϣ���ı�����ֱ�ӷ��͸��ͻ���
							out.print(msg.getMsg());
							// TODO Auto-generated method stub

						}
					};
					// ���ش����õ���Ϣ������
					return handler;
					// ���񴴽���Ϣ������ʱ�������쳣
				} catch (Exception ex) {
					// ����쳣��Ϣ
					ex.printStackTrace();
					// ���ؿյ���Ϣ������
					return null;
				}
			}

			/**
			 * ��ʼ��ʵʱ��Ϣ��ȡ����ķ���
			 * 
			 * @param request
			 *            ������Ϣ
			 * @param response
			 *            ��Ӧ����
			 * @param session
			 *            �Ự���ٶ���
			 * */
			@Override
			public void initService(HttpServletRequest request,
					HttpServletResponse response, HttpSession session) {
				// TODO Auto-generated method stub
				// ����ǰ�Ự���뵽ʵʱ��Ϣ�ȴ��б�
				kitchens.add(session.getId());
			}
		};

		template.getMsg(request, response, handler);

	}

}
