using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using nhom_13.data;
using nhom_13.Models;

namespace nhom_13.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ArticlesController : ControllerBase
    {
        private readonly MyDBContext _contenx;

        public ArticlesController(MyDBContext context)
        {
            _contenx = context;
        }

        [HttpGet]
        public async Task<IActionResult> GetAll()
        {
            var Articles = _contenx.Articles.Select(a => new ArticleVM
            {
                Id = a.Id,
                Title = a.Title,
                Content = a.Content,
                Time = a.Time,
                IsHotNews = a.IsHotNews,
                CategoryId = a.CategoryId,
            });
            return Ok(Articles.ToList());
        }


        [HttpGet("getArticlesVM2")]
        public async Task<IActionResult> getAllArticlesVM2()
        {
            var result = _contenx.Articles.Select(ar => new ArticleVM2
            {
                Id = ar.Id,
                Title = ar.Title,
                Content = ar.Content,
                Time = ar.Time,
                IsHotNews = ar.IsHotNews,
                CategoryId = ar.CategoryId,
                ImageName = _contenx.Images.Where(i => i.ArticleId == ar.Id).FirstOrDefault().Name,
            }).OrderByDescending(ar => ar.Time);

            return Ok(result.ToList());
        }







        [HttpGet("{Id}")]
        public IActionResult GetById(int Id)
        {
            var article = _contenx.Articles.SingleOrDefault(ar => ar.Id == Id);

            if (article == null)
            {
                return NotFound();
            }

            var ar = new ArticleVM
            {
                Id = article.Id,
                Title = article.Title,
                Content = article.Content,
                Time = article.Time,
                IsHotNews = article.IsHotNews,
                CategoryId = article.CategoryId,

            };
            return Ok(ar);

        }


        [HttpPost]
        public async Task<IActionResult> CreateNew(ArticleModel model)
        {
            try
            {
                var article = new Article
                {
                    Title = model.Title,
                    Content = model.Content,
                    IsHotNews= model.IsHotNews,
                    CategoryId = model.CategoryId,
                };
               
                _contenx.Add(article);
                _contenx.SaveChanges();
                return Ok(article);
            }
            catch
            {
                return BadRequest();
            }
        }

        [HttpPut("{Id}")]
        public async Task<IActionResult> UpdateById(int Id, ArticleModel model)
        {
            var article = _contenx.Articles.SingleOrDefault(x => x.Id == Id);
            if (article == null)
            {
                return NotFound();
            }
            article.Title = model.Title;
            article.Content = model.Content;
            article.IsHotNews = model.IsHotNews;
            article.CategoryId = model.CategoryId;
            article.Category = _contenx.Categories.Where(_ca => _ca.Id == model.CategoryId).FirstOrDefault();
            _contenx.SaveChanges();
            return Ok(article);
        }

        [HttpDelete("{Id}")]
        public async Task<IActionResult> DeleteById(int Id)
        {
            var article = _contenx.Articles.SingleOrDefault(ar => ar.Id == Id);
            if (article == null)
            {
                return NotFound();
            }
            _contenx.Remove(article);
            _contenx.SaveChanges();
            return Ok();
        }

        [HttpGet("{CategoryId}/getArticlesByCategoryId")]
        public async Task<IActionResult> GetArticlesByCategoryId(int CategoryId)
        {
            var category = _contenx.Categories.SingleOrDefault(x => x.Id == CategoryId);

            if (category == null)
            {
                return NotFound();
            }

            var articles = _contenx.Articles.Where(ar => ar.CategoryId == CategoryId);

            var result = articles.Select(ar => new ArticleVM2
            {
                Id = ar.Id,
                Title = ar.Title,
                Content = ar.Content,
                Time = ar.Time,
                IsHotNews = ar.IsHotNews,
                CategoryId = CategoryId,
                ImageName = _contenx.Images.Where(i => i.ArticleId == ar.Id).FirstOrDefault().Name,
            }).OrderByDescending(ar => ar.Time);

            return Ok(result.ToList());
        }
    }
}
