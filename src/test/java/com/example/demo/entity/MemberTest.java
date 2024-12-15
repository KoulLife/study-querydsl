package com.example.demo.entity;

import static com.example.demo.entity.QMember.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
class MemberTest {

	@Autowired
	EntityManager em;
	JPAQueryFactory queryFactory;

	@BeforeEach
	public void setEntity() {
		queryFactory = new JPAQueryFactory(em);
		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");
		em.persist(teamA);
		em.persist(teamB);

		Member member1 = new Member("member1", 10, teamA);
		Member member2 = new Member("member2", 20, teamB);
		Member member3 = new Member("member3", 30, teamB);
		Member member4 = new Member("member4", 40, teamB);

		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
		em.persist(member4);
	}

	@Test
	public void JPQLTest() {
		// JPQL
		Member findMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
			.setParameter("username", "member1")
			.getSingleResult();

		Assertions.assertThat(findMember.getUsername()).isEqualTo("member1");
	}

	// @Test
	// public void QueryDslTest() {
	// 	// QueryDSL
	// 	JPAQueryFactory queryFactory = new JPAQueryFactory(em);
	// 	QMember m = new QMember("m");
	//
	// 	Member findMember2 = queryFactory
	// 		.select(m)
	// 		.from(m)
	// 		.where(m.username.eq("member1"))
	// 		.fetchOne();
	//
	// 	Assertions.assertThat(findMember2.getUsername()).isEqualTo("member1");
	// }

	// 축약 버전 (권장)
	@Test
	public void QueryDslTest() {

		Member findMember2 = queryFactory
			.select(member)
			.from(member)
			.where(member.username.eq("member1"))
			.fetchOne();

		Assertions.assertThat(findMember2.getUsername()).isEqualTo("member1");
	}

}