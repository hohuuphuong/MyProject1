namespace nhom_13.data
{
    public class Category
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public ICollection<Article> Articles { get; set; }
        public Category() 
        {
            Articles = new List<Article>();
        }
    }
}
