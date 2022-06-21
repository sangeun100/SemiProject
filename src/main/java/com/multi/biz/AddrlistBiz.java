package com.multi.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.frame.Biz;
import com.multi.mapper.AddrlistMapper;
import com.multi.vo.AddrlistVO;

@Service
public class AddrlistBiz implements Biz<Integer, AddrlistVO> {

	@Autowired
	AddrlistMapper dao;

	@Override
	public void register(AddrlistVO v) throws Exception {
		dao.insert(v);
	}

	@Override
	public void modify(AddrlistVO v) throws Exception {
		dao.update(v);

	}

	@Override
	public void remove(Integer k) throws Exception {
		dao.delete(k);

	}

	@Override
	public AddrlistVO get(Integer k) throws Exception {
		return dao.select(k);
	}

	@Override
	public List<AddrlistVO> get() throws Exception {
		return dao.selectall();
	}
	
	public List<AddrlistVO> getpercust(String uid) throws Exception {
		return dao.selectpercust(uid);
	}

}
