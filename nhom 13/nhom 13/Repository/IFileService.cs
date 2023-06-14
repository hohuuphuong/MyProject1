namespace nhom_13.Repository
{
    public interface IFileService
    {
        public Tuple<int, string> SaveImage(IFormFile imageFile);

        public bool DeleteImage(string imageFileName);
    }
}
