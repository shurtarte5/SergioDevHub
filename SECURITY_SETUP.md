# 🛡️ Security Setup - TMDB API Key Configuration

## ⚠️ Important Security Notice
This project requires a TMDB API key that must be configured as an environment variable for security reasons.

## 🔧 Setup Instructions

### 1. Get your TMDB API Key
1. Go to [The Movie Database (TMDB)](https://www.themoviedb.org/)
2. Create an account or log in
3. Navigate to Settings > API
4. Request an API key
5. Copy your API key

### 2. Configure API Key

#### Option A: Environment Variable (Recommended for CI/CD)

##### For macOS/Linux (Bash/Zsh):
```bash
# Add to ~/.zshrc or ~/.bashrc
echo 'export TMDB_API_KEY=your_actual_api_key_here' >> ~/.zshrc
source ~/.zshrc
```

##### For Windows (PowerShell):
```powershell
[System.Environment]::SetEnvironmentVariable('TMDB_API_KEY', 'your_actual_api_key_here', 'User')
```

##### For Windows (Command Prompt):
```cmd
setx TMDB_API_KEY "your_actual_api_key_here"
```

#### Option B: Gradle Properties (Easier for local development)

1. Copy `gradle.properties.example` to `gradle.properties`
2. Replace `your_tmdb_api_key_here` with your actual API key
3. **Never commit `gradle.properties` to version control**

```bash
cp gradle.properties.example gradle.properties
# Edit gradle.properties and add your API key
```

### 3. Verify Configuration
```bash
echo $TMDB_API_KEY
```

## 🚨 Security Best Practices

✅ **DO:**
- Use environment variables for API keys
- Keep local.properties in .gitignore
- Never commit API keys to version control
- Use different keys for development/production

❌ **DON'T:**
- Store API keys in code files
- Share API keys in chat/email
- Commit sensitive data to repositories
- Use production keys in development

## 🔧 Build Configuration

The app will automatically:
- Read the API key from the environment variable
- Fail the build if the variable is not set
- Securely inject the key into BuildConfig

## 📝 Notes

- If you get a build error, ensure `TMDB_API_KEY` environment variable is set
- Restart your IDE after setting environment variables
- The API key is only accessible in the app at runtime, not in the source code