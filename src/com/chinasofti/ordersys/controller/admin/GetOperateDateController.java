package com.chinasofti.ordersys.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.chinasofti.ordersys.service.login.waiters.OrderService;
import com.chinasofti.ordersys.vo.OrderInfo;

@Controller
public class GetOperateDateController {
	@Autowired
	OrderService service;

	public OrderService getService() {
		return service;
	}

	public void setService(OrderService service) {
		this.service = service;
	}

	@RequestMapping("/getoperatedate")
	public void getOperateDate(HttpServletRequest request,
			HttpServletResponse response) {
		// ���÷��ص�MIME����Ϊxml
		response.setContentType("text/xml");
		// ���Դ����������ݽ��XML
		try {

			// �������ڸ�ʽ������
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			// ��ȡ��ʼʱ��
			Date begin = sdf.parse(request.getParameter("bt"));
			// ��ȡ����ʱ��
			Date end = sdf.parse(request.getParameter("et"));
			// ��ѯ�ᵥʱ���ڿ�ʼʱ�������ʱ��֮������ж�����Ϣ
			List<OrderInfo> list = service.getOrderInfoBetweenDate(begin,
					end);
			// ����XML DOM��
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			// ����XML���ڵ�
			Element root = doc.createElement("orders");
			// �����ڵ����DOM��
			doc.appendChild(root);
			// ѭ��������������еĶ�����Ϣ
			for (OrderInfo info : list) {
				// ��ȡÿ���������ܼ�
				float sumPrice = service.getSumPriceByOrderId(new Integer(info
						.getOrderId()));
				// // ÿһ����������һ��������ǩ�ڵ�
				Element order = doc.createElement("order");
				// ��������id��ǩ
				Element orderId = doc.createElement("orderId");
				// ���ö���id��ǩ�ı�����
				orderId.setTextContent(info.getOrderId() + "");
				// ������id��ǩ����Ϊ������ǩ���ӱ�ǩ
				order.appendChild(orderId);
				// �������ű�ǩ
				Element tableId = doc.createElement("tableId");
				// �������ű�ǩ�ı�����
				tableId.setTextContent(info.getTableId() + "");
				// �����ű�ǩ����Ϊ������ǩ�ӱ�ǩ
				order.appendChild(tableId);
				// �����ܼ۱�ǩ
				Element sumPriceElement = doc.createElement("sumPrice");
				// �����ܼ۱�ǩ�ı�����
				sumPriceElement.setTextContent(sumPrice + "");
				// ���ܼ۱�ǩ����Ϊ������ǩ�ӱ�ǩ
				order.appendChild(sumPriceElement);
				// ������ͷ���Ա�û�����ǩ
				Element userAccount = doc.createElement("userAccount");
				// ���õ�ͷ���Ա�û�����ǩ�ı�����
				userAccount.setTextContent(info.getUserAccount());
				// ����ͷ���Ա�û�����ǩ����Ϊ������ǩ�ӱ�ǩ
				order.appendChild(userAccount);
				// ���������ᵥʱ���ǩ
				Element orderEndDate = doc.createElement("orderEndDate");
				// ����ʱ�䡢���ڸ�ʽ������
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// ���ýᵥʱ���ǩ����Ϊ��ʽ�����ʱ���ַ���
				orderEndDate.setTextContent(sdf.format(info.getOrderEndDate()));
				// ���ᵥʱ���ǩ����Ϊ������ǩ�ӱ�ǩ
				order.appendChild(orderEndDate);
				// ��������ǩ����Ϊ����ǩ�ӱ�ǩ
				root.appendChild(order);

			}
			// ��������DOM��ת��ΪXML�ĵ��ṹ�ַ���������ͻ���
			TransformerFactory
					.newInstance()
					.newTransformer()
					.transform(new DOMSource(doc),
							new StreamResult(response.getOutputStream()));

			// �����ѯ��ת�������е��쳣��Ϣ
		} catch (Exception ex) {
			// ����쳣��Ϣ
			ex.printStackTrace();
		}

	}
}
