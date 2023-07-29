using nhom_13.data;

namespace nhom_13.Models
{
    public class ArticleModel
    {
        public string Title { get; set; }

        public string Content { get; set; }

        public Boolean IsHotNews { get; set; }

        public int CategoryId { get; set; }
      
    }

    public class ArticleVM
    {

        public int Id { get; set; }
        
        public string Title { get; set; }

        public string Content { get; set; }

        public DateTime Time { get; set; }

        public Boolean IsHotNews { get; set; }

        public int CategoryId { get; set; }
        
    }

    public class ArticleVM2
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Content { get; set; }
        public DateTime Time { get; set; }
        public Boolean IsHotNews { get; set; }
        public int CategoryId { get; set; }
        public String ImageName { get; set; }
    }
}
