using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using nhom_13.data;
using nhom_13.Models;

namespace nhom_13.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CategorysController : ControllerBase
    {
        private readonly MyDBContext _context;

        public CategorysController(MyDBContext context) 
        {
            _context = context;
        }

        [HttpGet]
        public async Task<IActionResult> GetAll()
        {
            var Categorys = _context.Categories.Select(ca => new CategoryVM
            {
                Id = ca.Id,
                Name = ca.Name,
            });
            return Ok(Categorys.ToList());
        }

        [HttpGet("{Id}")]
        public async Task<IActionResult> GetById(int Id)
        {
            var category = _context.Categories.SingleOrDefault(x => x.Id == Id);
            if (category == null)
            {
                return NotFound();
            }
            var ca = new CategoryVM
            {
                Id= category.Id,
                Name = category.Name,
            };
            return Ok(ca);
        }


        [HttpPost]
        public async Task<IActionResult> CreateNew(CategoryModel model)
        {
            try
            {
                var category = new Category
                {
                    Name = model.Name,
                };
                //category.Articles = _context.Articles.Where(ar => ar.CategoryId == category.Id).ToList();
                _context.Add(category);
                _context.SaveChanges();
                return Ok(category);
            }
            catch
            {
                return BadRequest();
            }
        }

        [HttpPut("{Id}")]
        public async Task<IActionResult> UpdateById(int Id, CategoryModel model)
        {
            var category = _context.Categories.SingleOrDefault(x => x.Id == Id);
            if (category == null)
            {
                return NotFound();
            }
            category.Name = model.Name;
            _context.SaveChanges();
            return Ok(category);
        }

        [HttpDelete("{Id}")]
        public async Task<IActionResult> DeleteById(int Id)
        {
            var category = _context.Categories.SingleOrDefault(ca => ca.Id == Id);
            if (category == null)
            {
                return NotFound();
            }
            _context.Remove(category);
            _context.SaveChanges();
            return Ok();
        }
    }
}
