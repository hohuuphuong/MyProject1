namespace nhom_13.Repository
{
    public class FileService : IFileService
    {
        private IWebHostEnvironment _environment;
        public FileService(IWebHostEnvironment environment)
        {
            _environment = environment;
        }

        public Tuple<int, string> SaveImage(IFormFile imageFile)
        {
            try
            {
                var contentPath = _environment.ContentRootPath;
                var path = Path.Combine(contentPath, "Upload");
                if (!Directory.Exists(path))
                {
                    Directory.CreateDirectory(path);
                }
                var ext = Path.GetExtension(imageFile.FileName);
                var allowedExtensions = new string[] { ".jpg", ".png", ".jpeg" };
                if(!allowedExtensions.Contains(ext))
                {
                    string msg = string.Format("Only {0} extensions are allowed");
                    return new Tuple<int, string> ( 0, msg );
                }
                string uniqueString = Guid.NewGuid().ToString();
                var newFileName = uniqueString + ext;
                var fileWithPath = Path.Combine(path, newFileName);
                var stream = new FileStream(fileWithPath, FileMode.Create);
                imageFile.CopyTo(stream);
                stream.Close();
                return new Tuple< int, string>( 1, newFileName );
            }
            catch(Exception ex )
            { 
                return new Tuple<int, string>( 0, "error" ); 
            }
        }

        public bool DeleteImage(string imageFileName)
        {
            try
            {
                var wwwPath = _environment.WebRootPath;
                var path = Path.Combine(wwwPath, "Upload\\", imageFileName);
                if(System.IO.File.Exists(path))
                {
                    System.IO.File.Delete(path);
                    return true;
                }
                return false;
            }
            catch(Exception ex)
            { 
                return false;
            }
        }
    }
}
