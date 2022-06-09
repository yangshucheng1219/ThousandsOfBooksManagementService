package com.wanjuanshu.service.impl;

import com.wanjuanshu.dao.PersonInfoDao;
import com.wanjuanshu.dto.PersonInfoExecution;
import com.wanjuanshu.enums.PersonInfoStateEnum;
import com.wanjuanshu.entity.PersonInfo;
import com.wanjuanshu.exceptions.PersonInfoOperationException;
import com.wanjuanshu.service.PersonInfoService;
import com.wanjuanshu.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {
	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public PersonInfo getPersonInfoById(Long userId) {
		return personInfoDao.queryPersonInfoById(userId);
	}

	@Override
	public PersonInfoExecution getPersonInfoList(PersonInfo personInfoCondition, int pageIndex, int pageSize) {
		// 页转行
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		// 获取用户信息列表
		List<PersonInfo> personInfoList = personInfoDao.queryPersonInfoList(personInfoCondition, rowIndex, pageSize);
		int count = personInfoDao.queryPersonInfoCount(personInfoCondition);
		PersonInfoExecution se = new PersonInfoExecution();
		if (personInfoList != null) {
			se.setPersonInfoList(personInfoList);
			se.setCount(count);
		} else {
			se.setState(PersonInfoStateEnum.INNER_ERROR.getState());
		}
		return se;
	}

	@Override
	@Transactional
	public PersonInfoExecution modifyPersonInfo(PersonInfo personInfo) {
		// 空值判断，主要是判断用户Id是否为空
		if (personInfo == null || personInfo.getUserId() == null) {
			return new PersonInfoExecution(PersonInfoStateEnum.EMPTY);
		} else {
			try {
				// 更新用户信息
				int effectedNum = personInfoDao.updatePersonInfo(personInfo);
				if (effectedNum <= 0) {
					return new PersonInfoExecution(PersonInfoStateEnum.INNER_ERROR);
				} else {
					personInfo = personInfoDao.queryPersonInfoById(personInfo.getUserId());
					return new PersonInfoExecution(PersonInfoStateEnum.SUCCESS, personInfo);
				}
			} catch (Exception e) {
				throw new PersonInfoOperationException("updatePersonInfo error: " + e.getMessage());
			}
		}
	}
}
