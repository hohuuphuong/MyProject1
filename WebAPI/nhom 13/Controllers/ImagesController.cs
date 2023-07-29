using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using nhom_13.data;
using nhom_13.Models;
using nhom_13.Repository;

namespace nhom_13.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ImagesController : ControllerBase
    {
        //private readonly IFileService _fileService;
        //private readonly IImageRepository _imageRepository;
        private IWebHostEnvironment _webHostEnvironment;
        private readonly MyDBContext _context;

        public ImagesController(IWebHostEnvironment webHostEnvironment, MyDBContext context) 
        {
            _webHostEnvironment = webHostEnvironment;
            _context = context;
        }

        /*[HttpPost]
         public async Task<IActionResult> Add([FromForm]ImageModel model)
         {
             if(!ModelState.IsValid)
             {
                 return BadRequest(ModelState);
             }
             if(model.File != null)
             {
                 var fileResult = _fileService.SaveImage(model.File);
                 if(fileResult.Item1==1) 
                 {
                     model.Name = fileResult.Item2;
                 }
                 var imageResult = _imageRepository.Add(model);

             }
             return Ok();
         }*/
        [HttpPost]
        public async Task<String> Create([FromForm] ImageModel imageModel)
        {
            try
            {
                if(imageModel.File.Length > 0)
                {
                    string path = _webHostEnvironment.WebRootPath + "\\uploads\\";
                    if (!Directory.Exists(path))
                    {
                        Directory.CreateDirectory(path);
                    }
                    var img = new Image
                    {
                        Name = "http://10.0.2.2:5165/api/Images/" + imageModel.File.FileName,
                        ArticleId = imageModel.ArticleId,
        
                    };
                    _context.Add(img);
                    _context.SaveChanges();

                    using (FileStream fileSteam = System.IO.File.Create(path + imageModel.File.FileName))
                    {
                        imageModel.File.CopyTo(fileSteam);
                        fileSteam.Flush();
  
                    }
                    
                    return "Done.";
                }
                else
                {
                    return "Failed";
                }
            }
            catch (Exception ex)
            {
                return ex.Message;
            }
        }

        [HttpGet]
        public async Task<IActionResult> GetAllImage()
        {
            var img = _context.Images.Select(i => new ImageVM
            {
                Id = i.Id,
                Name = i.Name,
                ArticleId = i.ArticleId,
            });
            return Ok(img.ToList());
        }



        [HttpGet("{fileName}")]
        public async Task<IActionResult> Get([FromRoute] string fileName)
        {
            string path = _webHostEnvironment.WebRootPath + "\\uploads\\";
            var filePath = path + fileName;
            if (System.IO.File.Exists(filePath))
            {
                var ext = Path.GetExtension(fileName).TrimStart('.');
                byte[] b = System.IO.File.ReadAllBytes(filePath);
                return File(b, $"image/{ext}");
            }
            return BadRequest();
        }
    }
}
