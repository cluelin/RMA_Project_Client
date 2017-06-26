package AdvancedReplacement;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.JTextComponent;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Communication.Communication;
import Communication.ConnectionSocket;

public class ComboBoxListener implements DocumentListener {

	GUIadvancedRepalcementPanel guiAdvancedRepalcementPanel = GUIadvancedRepalcementPanel.getInstance();

	public ComboBoxListener() {

		// companyUpdate();

	}

	private void companyUpdate() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override

			public void run() {
				showRecommendCompanyList();
			}

		});

	}

	private void siteUpdate() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override

			public void run() {

				showRecommendSiteList();

			}

		});

	}

	private void itemUpdate() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override

			public void run() {

				showRecommendItemList();

			}

		});

	}

	private void showRecommendCompanyList() {

		// 이벤트 발생지를 Company로 조정한다.
		JComboBox owner = guiAdvancedRepalcementPanel.getTxtCompanyName();
		Component component = owner.getEditor().getEditorComponent();
		JTextComponent textComponent = (JTextComponent) component;

		String targetName = textComponent.getText();

		// 현재 입력창에 입력된 값(targetName)을 기준으로 추천단어를 검색해서 리스트에 등록.
		List<String> targetList = Communication.getInstance().getCompanyNameListFromServer(targetName);

		// targetName과 같은 회사의 이전 RMA정보를 출력.
		Communication.getInstance().showPreviousRMAList(targetName, "");

		Set<String> foundSet = new HashSet<String>();

		for (String temp : targetList) {

			foundSet.add(temp.toLowerCase());

		}

		Collections.sort(targetList);// sort alphabetically

		owner.setEditable(false);

		owner.removeAllItems();

		// if founds contains the search text, then only add
		// once.

		if (!foundSet.contains(targetName.toLowerCase())) {

			owner.addItem(targetName);
			guiAdvancedRepalcementPanel.clearCompanyDetail();

		} else {

			// 검색어와 일치하는 회사가 있을경우 정보를 가져옴.

			JSONObject companyDetailObject = Communication.getInstance().getCompanyDetailJSON(targetName);

			String address = companyDetailObject.get("companyAddress").toString();
			String city = companyDetailObject.get("companyCity").toString();
			String zipCode = companyDetailObject.get("companyZipCode").toString();
			String phone = companyDetailObject.get("companyPhone").toString();
			String email = companyDetailObject.get("companyEmail").toString();

			guiAdvancedRepalcementPanel.setCompanyDetail(address, city, zipCode, phone, email);

			guiAdvancedRepalcementPanel.setBillToArea(targetName, address, city, zipCode, phone);

		}

		for (String temp : targetList) {

			owner.addItem(temp);

		}

		showRecommendSiteList();

		owner.setPopupVisible(true);
		owner.setEditable(true);
		owner.requestFocus();

	}

	private void showRecommendSiteList() {

		JComboBox owner = guiAdvancedRepalcementPanel.getTxtSiteName();

		Component component = owner.getEditor().getEditorComponent();
		JTextComponent textComponent = (JTextComponent) component;

		String siteName = textComponent.getText();
		String companyName = guiAdvancedRepalcementPanel.getTxtCompanyName().getEditor().getItem().toString();

		

		List<String> founds = Communication.getInstance().getSiteNameListFromServer(siteName, companyName);

		Set<String> foundSet = new HashSet<String>();

		for (String s : founds) {

			foundSet.add(s.toLowerCase());

		}

		Collections.sort(founds);// sort alphabetically

		owner.setEditable(false);

		owner.removeAllItems();

		// if founds contains the search text, then only add
		// once.

		if (!foundSet.contains(siteName.toLowerCase())) {

			owner.addItem(siteName);

		} else {

			// 일치하는 경우

			

		}
		
		System.out.println("siteName : " + siteName);
		
		if(siteName.equals("")){
			System.out.println("siteName 공백 ");
		}
		
		Communication.getInstance().showPreviousRMAList(companyName, siteName);
		

		for (String s : founds) {

			owner.addItem(s);

		}

		owner.setPopupVisible(true);
		owner.setEditable(true);
		owner.requestFocus();
	}

	private void showRecommendItemList() {

		JComboBox owner = guiAdvancedRepalcementPanel.getItemComboBox();
		Component component = owner.getEditor().getEditorComponent();
		JTextComponent textComponent = (JTextComponent) component;

		String partialOfItemName = textComponent.getText();

		// Keyword Result list
		List<String> founds = Communication.getInstance().getItemNameFromServer(partialOfItemName);

		Set<String> foundSet = new HashSet<String>();

		for (String temp : founds) {

			foundSet.add(temp.toLowerCase());

		}

		Collections.sort(founds);// sort alphabetically

		DefaultComboBoxModel boxModel = new DefaultComboBoxModel();

		if (!foundSet.contains(partialOfItemName.toLowerCase())) {

			boxModel.addElement(partialOfItemName);

		} else {

		}

		for (String temp : founds) {

			boxModel.addElement(temp);

		}

		// owner.setEditable(false);

		owner.setModel(boxModel);

		// owner.addPopupMenuListener(new ItemPopupListener(founds));

		// owner.setPopupVisible(true);
		owner.setEditable(true);
		owner.requestFocus();

	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		System.out.println("insert Update");

		if (e.getDocument().getProperty("owner").equals("companyName")) {

			companyUpdate();

		} else if (e.getDocument().getProperty("owner").equals("siteName")) {

			siteUpdate();
		} else if (e.getDocument().getProperty("owner").equals("itemName")) {

			itemUpdate();
		}

	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		System.out.println("remove Update");

		if (e.getDocument().getProperty("owner").equals("companyName")) {

			companyUpdate();

		} else if (e.getDocument().getProperty("owner").equals("siteName")) {

			siteUpdate();
		} else if (e.getDocument().getProperty("owner").equals("itemName")) {

			itemUpdate();
		}

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		System.out.println("changed Update");

	}

}
