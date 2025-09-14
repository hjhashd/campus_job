package com.coding24h.campus_job.service.impl;

import com.coding24h.campus_job.dto.AdminJobDTO;
import com.coding24h.campus_job.dto.JobResultDTO;
import com.coding24h.campus_job.dto.JobSearchDTO;
import com.coding24h.campus_job.dto.PageResult;
import com.coding24h.campus_job.dto.forDetail.JobDetailDTO;
import com.coding24h.campus_job.entity.Job;
import com.coding24h.campus_job.entity.JobReview;
import com.coding24h.campus_job.entity.Page;
import com.coding24h.campus_job.exception.ResourceNotFoundException;
import com.coding24h.campus_job.mapper.JobMapper;
import com.coding24h.campus_job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobServiceImpl implements JobService {

    private final JobMapper jobMapper;

    private final JobReviewService jobReviewService;

    @Autowired
    public JobServiceImpl(JobMapper jobMapper, JobReviewService jobReviewService) {
        this.jobMapper = jobMapper;
        this.jobReviewService = jobReviewService;
    }

    @Override
    public List<JobResultDTO> getAllJobs() {
        return jobMapper.getAllJobs();
    }

    @Override
    public JobDetailDTO getJobDetailById(Long jobId) {
        JobDetailDTO jobDetail = jobMapper.selectJobDetailById(jobId);

        // 获取相似职位
        List<JobDetailDTO.SimilarJobDTO> similarJobs = jobMapper.selectSimilarJobs(jobId, 5);
        jobDetail.setSimilarJobs(similarJobs);

        // 处理职位描述的多行文本
        if (jobDetail != null && jobDetail.getDescription() != null) {
            String formattedDesc = jobDetail.getDescription().replace("\\n", "\n");
            jobDetail.setDescription(formattedDesc);
        }


        return jobDetail;
    }



    @Override
    public PageResult<JobResultDTO> searchJobs(JobSearchDTO searchDTO) {
        int offset = (searchDTO.getPage() - 1) * searchDTO.getPageSize();

        // 修改Mapper返回JobResultDTO
        List<JobResultDTO> jobs = jobMapper.searchJobs(searchDTO, offset);
        long totalItems = jobMapper.countJobs(searchDTO);

        int totalPages = (int) Math.ceil((double) totalItems / searchDTO.getPageSize());

        return new PageResult<>(
                totalItems,
                totalPages,
                searchDTO.getPage(),
                searchDTO.getPageSize(),
                jobs
        );
    }

    @Override
    public void publishJob(Job job) {
        // 设置创建时间为当前时间
        job.setCreatedAt(LocalDateTime.now());
        jobMapper.insertJob(job);
    }


    public Job getJobById(Long jobId) {
        return jobMapper.findById(jobId);
    }


    @Override
    @Transactional
    public void updateJob(Job job) {
        int affectedRows = jobMapper.updateJob(job);
        if (affectedRows == 0) {
            throw new ResourceNotFoundException("职位不存在或您无权修改");
        }
    }

    @Override
    @Transactional
    public void deleteJob(Long jobId, Long companyUserId) {
        int affectedRows = jobMapper.deleteJob(jobId, companyUserId);
        if (affectedRows == 0) {
            throw new ResourceNotFoundException("职位不存在或您无权删除");
        }
    }

    @Override
    public PageResult<Job> listJobsByCompany(Long companyUserId, int page, int size) {
        int offset = (page - 1) * size;
        List<Job> jobs = jobMapper.findJobsByCompany(companyUserId, offset, size);
        int total = jobMapper.countJobsByCompany(companyUserId);
        int totalPages = (int) Math.ceil((double) total / size);

        return new PageResult<>(
                page,
                size,
                total,
                totalPages,
                jobs
        );
    }

    @Override
    public Job getJobByIdAndCompany(Long jobId, Long companyUserId) {
        Job job = jobMapper.findById(jobId);
        if (job == null || !job.getCompanyUserId().equals(companyUserId)) {
            throw new ResourceNotFoundException("职位不存在");
        }
        return job;
    }

    @Override
    public List<JobReview> getReviewsByJobId(Long jobId) {
        return jobMapper.findReviewsWithJobTitle(jobId);
    }


    public PageResult<AdminJobDTO> getAdminJobs(int page, int size, Map<String, String> filters) {
        int offset = (page - 1) * size;
        List<AdminJobDTO> jobs = jobMapper.findAdminJobs(offset, size, filters);
        long totalItems = jobMapper.countAdminJobs(filters);
        int totalPages = (int) Math.ceil((double) totalItems / size);

        // 使用修改后的构造函数
        return new PageResult<>(totalItems, totalPages, page, size, jobs);
    }

    public Map<String, Long> getJobStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", jobMapper.countAllJobs());
        stats.put("lowRisk", jobMapper.countJobsByRiskLevel("LOW"));
        stats.put("mediumRisk", jobMapper.countJobsByRiskLevel("MEDIUM"));
        stats.put("highRisk", jobMapper.countJobsByRiskLevel("HIGH"));
        return stats;
    }

    @Transactional
    public void deleteJobAdmin(Long jobId) {
        // 先删除关联的评论
        jobMapper.deleteReviewsByJobId(jobId);
        // 再删除职位
        jobMapper.deleteJobAdmin(jobId);
    }

    public PageResult<JobReview> getAllReviewsWithJobInfo(int page, int size) {
        // 计算偏移量
        int offset = (page - 1) * size;

        // 获取总记录数
        long totalItems = jobMapper.countAllReviews();

        // 计算总页数
        int totalPages = (int) Math.ceil((double) totalItems / size);

        // 获取当前页数据
        List<JobReview> items = jobMapper.findReviewsWithJobInfo(offset, size);

        return new PageResult<>(totalItems, totalPages, page, size, items);
    }
}