namespace nhom_13.data
{
    public class Image
    {
        public int Id { get; set; }

        public string Name { get; set; }

        //public IFormFile File { get; set; }
        
        public int ArticleId { get; set; }
        public Article Article { get; set; }
    }
}
