package ru.prbb.middleoffice.repo;

import java.util.List;

import ru.prbb.middleoffice.domain.Member;

public interface MemberDao
{
    public Member findById(Long id);

    public Member findByEmail(String email);

    public List<Member> findAllOrderedByName();

    public void register(Member member);

	public Long updateById(Long id, Member value);

	public Long deleteById(Long id);
}
