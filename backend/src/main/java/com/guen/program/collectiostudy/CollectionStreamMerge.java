package com.guen.program.collectiostudy;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***************************************************************************************************
    team 별
    member의 teamName,teamId , memberNm , age , age의 평균을 구하고
    age로 내림차순 정렬해라
****************************************************************************************************/
public class CollectionStreamMerge {

    @Getter
    public static class team{
        private int teamId;
        private String teamName;

        public team(int teamId, String teamName) {
            this.teamId = teamId;
            this.teamName = teamName;
        }
    }
    @Getter
    public static class member{
        private int memberId;
        private int teamId;
        private String memberNm;

        private int age;

        public member(int memberId, int teamId, String memberNm, int age) {
            this.memberId = memberId;
            this.teamId = teamId;
            this.memberNm = memberNm;
            this.age = age;
        }
    }

    @Getter
    public static class dto{
        private int teamId;
        private String teamName;

        private String memberNm;

        private int age;

        private double avgAge;

        public dto(int teamId, String teamName, String memberNm, int age, double avgAge) {
            this.teamId = teamId;
            this.teamName = teamName;
            this.memberNm = memberNm;
            this.age = age;
            this.avgAge = avgAge;
        }

        @Override
        public String toString() {
            return "dto{" +
                    "teamId=" + teamId +
                    ", teamName='" + teamName + '\'' +
                    ", memberNm='" + memberNm + '\'' +
                    ", age=" + age +
                    ", avgAge=" + avgAge +
                    '}';
        }
    }

    public static void main(String[] args) {
        List<CollectionStreamMerge.team> teams = Arrays.asList(
                new CollectionStreamMerge.team(1, "grab"),
                new CollectionStreamMerge.team(2, "baidu"),
                new CollectionStreamMerge.team(3, "nvdia")
        );

        List<CollectionStreamMerge.member> members = Arrays.asList(
                new CollectionStreamMerge.member(1, 1, "m1", 46),
                new CollectionStreamMerge.member(2, 1, "'m2", 47),
                new CollectionStreamMerge.member(3, 1, "'m3", 48),
                new CollectionStreamMerge.member(4, 1, "'m4", 49),
                new CollectionStreamMerge.member(5, 1, "'m5", 50),
                new CollectionStreamMerge.member(6, 1, "'m6", 51),
                new CollectionStreamMerge.member(7, 1, "'m7", 52),
                new CollectionStreamMerge.member(8, 1, "'m8", 53),
                new CollectionStreamMerge.member(9, 1, "'m9", 54),
                new CollectionStreamMerge.member(10, 2, "'m10", 55),
                new CollectionStreamMerge.member(11, 2, "'m11", 56),
                new CollectionStreamMerge.member(12, 2, "'m12", 57)
        );



        // teamId를 기준으로 member를 그룹화
        Map<Integer, List<member>> groupedMembers = members.stream()
                .collect(Collectors.groupingBy(member::getTeamId));

        // 각 그룹의 teamName, teamId, memberNm, age 및 age의 평균을 계산
        List<CollectionStreamMerge.dto> result = new ArrayList<>();
        for (team team : teams) {
            List<member> teamMembers = groupedMembers.get(team.getTeamId());
            if (teamMembers != null) {
                double avgAge = teamMembers.stream()
                        .mapToInt(member::getAge)
                        .average()
                        .orElse(0.0);

                for (member member : teamMembers) {
                    CollectionStreamMerge.dto dto = new CollectionStreamMerge.dto(
                            team.getTeamId(),
                            team.getTeamName(),
                            member.getMemberNm(),
                            member.getAge(),
                            avgAge
                    );
                    result.add(dto);
                }
            }
        }

        // age를 기준으로 결과를 내림차순으로 정렬
        result.sort((dto1, dto2) -> Integer.compare((Integer)dto2.getAge() , (Integer) dto1.getAge()));

        // 결과 출력
        for (CollectionStreamMerge.dto dto : result) {
            System.out.println(dto.toString());
        }


    }

}
