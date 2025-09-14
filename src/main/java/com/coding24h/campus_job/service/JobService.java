package com.coding24h.campus_job.service;

import com.coding24h.campus_job.dto.AdminJobDTO;
import com.coding24h.campus_job.dto.JobResultDTO;
import com.coding24h.campus_job.dto.JobSearchDTO;
import com.coding24h.campus_job.dto.PageResult;
import com.coding24h.campus_job.dto.forDetail.JobDetailDTO;
import com.coding24h.campus_job.entity.Job;
import com.coding24h.campus_job.entity.JobReview;
import com.coding24h.campus_job.entity.Page;

import java.util.List;
import java.util.Map;

public interface JobService {


    List<JobResultDTO> getAllJobs();

    JobDetailDTO getJobDetailById(Long jobId);

    PageResult<JobResultDTO> searchJobs(JobSearchDTO searchDTO);

    void publishJob(Job job);

    Job getJobById(Long jobId);


    void updateJob(Job job);

   void deleteJob(Long jobId, Long companyUserId);

   PageResult<Job> listJobsByCompany(Long companyUserId, int page, int size) ;

    Job getJobByIdAndCompany(Long jobId, Long companyUserId);


    List<JobReview> getReviewsByJobId(Long jobId);

    PageResult<AdminJobDTO> getAdminJobs(int page, int size, Map<String, String> filters);

    Map<String, Long> getJobStats();

    public void deleteJobAdmin(Long jobId);

    PageResult<JobReview> getAllReviewsWithJobInfo(int page, int size);
}