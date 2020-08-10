package com.mobeom.local_currency.post;




import static com.mobeom.local_currency.post.QPost.post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


interface CustomPostRepository {
    Post findByPostId(long postId);
    List<Post> inquiryList();
}

@Repository
public class PostRepositoryImpl extends QuerydslRepositorySupport implements CustomPostRepository {

    private final JPAQueryFactory query;


    public PostRepositoryImpl(JPAQueryFactory query) {
        super(Post.class);
        this.query = query;
    }

    @Override
    public NoticeVO findByPostId(long postId) {
        QPost post = QPost.post;
        Post findOne = queryFactory.selectFrom(post).where(post.postId.eq(postId)).fetchOne();
        //int postId, String category, String postTitle, LocalDate regDate, int readCount
        NoticeVO resultVO = new NoticeVO(
                findOne.getPostId(),
                findOne.getCategory(),
                findOne.getPostTitle(),
                findOne.getRegDate(),
                findOne.getReadCount(),
                findOne.getContents()
        );
        return resultVO;
    }

    @Override
    public List<Post> List(String searchWord) {
        return null;
    }

    public List<NoticeVO> noticeList(){
        QPost post = QPost.post;
        List<Post> list = queryFactory.select(post).from(post).fetch();


    @Override
    public List<Post> inquiryList() {
        List<Post> resultList = query.selectFrom(post).where(post.category.like("%"+"문의"+"%")).fetch();
        return resultList;
    }


}