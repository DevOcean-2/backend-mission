package com.devocean.Balbalm.mission.repository.impl;

import com.devocean.Balbalm.mission.domain.dto.LandMarkMissionDto;
import com.devocean.Balbalm.mission.repository.custom.LandMarkMissionRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.devocean.Balbalm.mission.domain.entity.QLandMarkMission.landMarkMission;

@Repository
public class LandMarkMissionRepositoryImpl implements LandMarkMissionRepositoryCustom {

    private JPAQueryFactory queryFactory;

    @Autowired
    @Qualifier("entityManagerFactory")
    public void setEntityManager(EntityManager entityManagerFactory) {
        this.queryFactory = new JPAQueryFactory(entityManagerFactory);
    }

//    @Override
//    public List<LandMarkMissionDto> findAllByUserIdAndLocationMissionIds(String userId, List<Long> locationMissionIds) {
//        return queryFactory.select(
//                Projections.constructor(LandMarkMissionDto.class,
//                        landMarkMission.id,
//                        landMarkMission.locationMission,
//                        landMarkMission.userId,
//                        landMarkMission.count,
//                        landMarkMission.percent,
//                        landMarkMission.progressType,
//                        landMarkMission.isComplete,
//                        landMarkMission.completeDate)
//        )
//                .from(landMarkMission)
//                .where(landMarkMission.userId.eq(userId)
//                        .and(landMarkMission.locationMission.id.in(locationMissionIds))
//                )
//                .fetch();
//    }

}
