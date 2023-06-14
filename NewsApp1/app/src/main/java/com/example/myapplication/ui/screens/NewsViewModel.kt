import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.NewsApplication
import com.example.myapplication.data.NetworkCategoriesRepository
import com.example.myapplication.data.NewsArticlesRepository
import com.example.myapplication.data.NewsUiState
import com.example.myapplication.model.Article
import com.example.myapplication.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface ArticlesState {
    data class Success(val Articles: List<Article>, val Categories: List<Category>) : ArticlesState
    object Loading: ArticlesState
    object Error: ArticlesState
}

class NewsViewModel(
    private val newsArticlesRepository: NewsArticlesRepository,
    private val networkCategoriesRepository: NetworkCategoriesRepository
    )
    : ViewModel() {
    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState

    var category = Category(0,"")

    var articlesState: ArticlesState by mutableStateOf(ArticlesState.Loading)

    val savedArticles = mutableListOf<Article>()


    init {
        getArticles()
    }

    fun getArticles() {
        viewModelScope.launch {
            try{
                val articles = newsArticlesRepository.getArticles()
                val categories = networkCategoriesRepository.getCategories()

                category = categories[0]
                articlesState = ArticlesState.Success(articles, categories)
            }
            catch (e: HttpException) {
                Log.e("b", "HttpException")
                articlesState = ArticlesState.Error
            }
            catch (e: IOException) {
                Log.e("a", "IOException")
                articlesState = ArticlesState.Error
            }
        }
    }



    fun setCurrentTypeOfNews(currentTypeOfNews: Category) {
        _uiState.update { newsUiState ->
            newsUiState.copy(
                currentTypeOfNews = currentTypeOfNews
            )
        }
    }
    fun onClickAdd(article: Article) {
        savedArticles.add(article)
    }
    fun onClickUnadd(article: Article) {
        savedArticles.remove(article)
    }
    fun resetUiState() {
        _uiState.update {
            it.copy(
                currentTypeOfNews = category
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NewsApplication)
                val newsArticlesRepository = application.container.newsRepository
                val networkCategoriesRepository = application.container.networkCategoriesRepository
                NewsViewModel(newsArticlesRepository = newsArticlesRepository, networkCategoriesRepository = networkCategoriesRepository)
            }
        }
    }
}