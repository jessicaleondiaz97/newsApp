package com.NewsApp.Repository;
import com.NewsApp.Models.DataLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRepositoty extends MongoRepository<DataLog, Long> {
}
