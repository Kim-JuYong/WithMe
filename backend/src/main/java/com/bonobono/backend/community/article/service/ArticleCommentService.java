package com.bonobono.backend.community.article.service;

import com.bonobono.backend.community.article.dto.req.ArticleCommentRequestDto;
import com.bonobono.backend.community.article.dto.res.ArticleCommentResponseDto;
import com.bonobono.backend.community.article.entity.Article;
import com.bonobono.backend.community.article.entity.ArticleComment;
import com.bonobono.backend.community.article.repository.ArticleCommentRepository;
import com.bonobono.backend.community.article.repository.ArticleRepository;
import com.bonobono.backend.member.domain.Member;
import com.bonobono.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticleCommentService {

    private final ArticleRepository articleRepository;

    private final ArticleCommentRepository articleCommentRepository;

    private final MemberRepository memberRepository;

    // 댓글 작성하기
    @Transactional
    public ArticleCommentResponseDto save(Long articleId, ArticleCommentRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(()-> new IllegalArgumentException("해당 멤버가 없습니다. id=" + requestDto.getMemberId()));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id=" + articleId));

        ArticleComment parentComment = null;
        if (requestDto.getParentCommentId() != null) {
            parentComment = articleCommentRepository.findById(requestDto.getParentCommentId())
                    .orElseThrow(()-> new IllegalArgumentException("해당 댓글이 없습니다. id=" + articleId));
        }
        ArticleComment articleComment = articleCommentRepository.save(requestDto.toEntity(article, member, parentComment));
        if (parentComment != null){
            parentComment.addChildComment(articleComment);
        }
        return new ArticleCommentResponseDto(articleComment, member);

    }

    // 댓글 조회하기
    @Transactional
    public List<ArticleCommentResponseDto> findByArticleId(Long articleId, Member member){
        return articleCommentRepository.findAllByArticleIdAndParentCommentIsNull(articleId).stream()
                .map(articleComment -> new ArticleCommentResponseDto(articleComment, member))
                .collect(Collectors.toList());
    }

    // 댓글 수정하기
    @Transactional
    public ArticleCommentResponseDto update(Long articleId, Long commentId, ArticleCommentRequestDto requestDto) {
        if (!articleRepository.existsById(articleId)) {
            throw new IllegalArgumentException("해당 게시글이 없습니다. id=" + articleId);
        }
        ArticleComment articleComment = articleCommentRepository.findById(commentId)
                .orElseThrow(()-> new IllegalArgumentException("해당 댓글이 없습니다. id=" + commentId));
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(()-> new IllegalArgumentException("해당 멤버가 없습니다. id=" + requestDto.getMemberId()));
        articleComment.updateComment(requestDto.getContent());
        return new ArticleCommentResponseDto(articleComment, member);
    }

    // 댓글 삭제하기
    @Transactional
    public void delete(Long articleId, Long commentId){
        if (!articleRepository.existsById(articleId)) {
            throw new IllegalArgumentException("해당 게시글이 없습니다. id=" + articleId);
        }
        ArticleComment articleComment = articleCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + commentId));
        articleCommentRepository.delete(articleComment);
    }

}
