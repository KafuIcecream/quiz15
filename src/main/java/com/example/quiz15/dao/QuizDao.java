package com.example.quiz15.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.quiz15.entity.Quiz;

import jakarta.transaction.Transactional;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer>{
	
	@Transactional
	@Modifying
	@Query(value="insert into quiz(name,description,start_date,end_date,published) values(?1,?2,?3,?4,?5)",nativeQuery=true)
	public void insert(String name,String decription,LocalDate startDate,LocalDate endDate,boolean published);

	@Query(value="select max(id) from quiz",nativeQuery=true)
	public int getMaxQuizId();

	@Query(value="select count(id) from quiz where id=?1",nativeQuery=true)
	public int getCountByQuizId(int id);
	
	@Transactional
	@Modifying
	@Query(value="update quiz set name=?2,description=?3,start_date=?4,end_date=?5,published=?6 where id=?1",nativeQuery=true)
	public int update(int id,String name,String description,LocalDate startTime,LocalDate endTime,boolean published);
	
	@Query(value="select * from quiz",nativeQuery=true)
	public List<Quiz> getAll();
}
