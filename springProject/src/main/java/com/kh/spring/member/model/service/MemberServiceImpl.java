package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

// @Component 로 해도 bean 등록 가능
@Service
public class MemberServiceImpl implements MemberService{

//	private MemberDao mDao = new MemberDao();
	
	@Autowired //인젝션
	private MemberDao mDao;
	
	@Autowired //인젝션! 강제로 주입 : Spring에서 의존성(DI)을 자동으로 주입해주는 어노테이션이에요.
	private SqlSessionTemplate sqlSession;
	// 세션도 알아서 (단, bean으로 등록했는지 확인! root-context.xml에서 확인)
	// @Autowired)
	
	// getSession이든 new mDao 이런거 안 해도 됨 이제!
	
	
	
	@Override
	public Member loginMember(Member m) {
		
		return mDao.loginMember(sqlSession, m);
		// 반납도 스스로 해줌...
	}

	@Override
	public int insertMember(Member m) {
		return 0;
	}

	@Override
	public int updateMember(Member m) {
		return 0;
	}

	@Override
	public int deleteMember(String userId) {
		return 0;
	}

	@Override
	public int idCheck(String checkId) {
		return 0;
	}
	

}
