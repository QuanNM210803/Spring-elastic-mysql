package com.example.springelasticmysql.utils;

import com.example.springelasticmysql.mapper.UserMapper;
import com.example.springelasticmysql.model.User;
import com.example.springelasticmysql.repository.ElasticRepository;
import com.example.springelasticmysql.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ElasticSynchronizer {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ElasticRepository elasticRepository;

    private static Long count_run= 0L;
    private static final Logger LOG= LoggerFactory.getLogger(ElasticSynchronizer.class);

    // moi Constants.INTERVAL_IN_MILLISECOND la lai chay
    @Scheduled(fixedRate = 30000)
    @Transactional
    public void syncUsers(){

        // tao ra tieu chi truy van, tieu chi nay ap dung dieu kien truy van dua tren modificationDate của entity User
        // lambda- root la entity, query la cau truy van, criteriaBuilder la doi tuong dc dung de xay dung các bieu thuc(expression)
        // hoac dieu kien (predicate) trong cau truy van
        Specification<User> userSpecification=(root, query, criteriaBuilder) -> getModificationDatePredicate(criteriaBuilder, root);
        List<User> userList= elasticRepository.count()==0 ? userRepository.findAll():userRepository.findAll(userSpecification);

        for(User user:userList){
            LOG.info("Syncing User - {}", user.getId());
            elasticRepository.save(userMapper.toUserModel(user));
        }
    }

    private static Predicate getModificationDatePredicate(CriteriaBuilder cb, Root<?> root){
        // Lấy thời gian hiện tại từ Java
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Timestamp currentTimeMinus = new Timestamp(System.currentTimeMillis() - Constants.INTERVAL_IN_MILLISECOND);

        LOG.info("RUN-{} - MODIFICATION_DATE from {} to {}", count_run++, currentTimeMinus, currentTime);

        // kiem tra xem modificationDate cua tung nguoi co bi thay doi trong khoang thoi gian khong, neu co thi tra ve
        return cb.between(root.<Date>get(Constants.MODIFICATION_DATE), currentTimeMinus, currentTime);
    }
}
