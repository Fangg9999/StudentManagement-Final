<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đăng nhập - Quản lý Sinh viên</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            
            body {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                display: flex;
                align-items: center;
                justify-content: center;
                height: 100vh;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }
            
            .login-container {
                display: flex;
                width: 90%;
                max-width: 900px;
                background: white;
                border-radius: 20px;
                box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
                overflow: hidden;
                animation: slideIn 0.5s ease-out;
            }
            
            @keyframes slideIn {
                from {
                    opacity: 0;
                    transform: translateY(20px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }
            
            .login-left {
                flex: 1;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                padding: 60px 40px;
                display: flex;
                flex-direction: column;
                justify-content: center;
                color: white;
                position: relative;
                overflow: hidden;
            }
            
            .login-left::before {
                content: '';
                position: absolute;
                width: 300px;
                height: 300px;
                background: rgba(255, 255, 255, 0.1);
                border-radius: 50%;
                top: -100px;
                right: -100px;
                animation: float 6s ease-in-out infinite;
            }
            
            .login-left::after {
                content: '';
                position: absolute;
                width: 200px;
                height: 200px;
                background: rgba(255, 255, 255, 0.05);
                border-radius: 50%;
                bottom: -50px;
                left: -50px;
                animation: float 8s ease-in-out infinite reverse;
            }
            
            @keyframes float {
                0%, 100% {
                    transform: translateY(0px);
                }
                50% {
                    transform: translateY(-20px);
                }
            }
            
            .login-left h1 {
                font-size: 2.5rem;
                font-weight: 700;
                margin-bottom: 20px;
                position: relative;
                z-index: 1;
            }
            
            .login-left p {
                font-size: 1.1rem;
                opacity: 0.95;
                margin-bottom: 30px;
                position: relative;
                z-index: 1;
                line-height: 1.6;
            }
            
            .feature-list {
                position: relative;
                z-index: 1;
            }
            
            .feature-item {
                display: flex;
                align-items: center;
                margin-bottom: 15px;
                animation: fadeInUp 0.6s ease-out forwards;
                opacity: 0;
            }
            
            .feature-item:nth-child(1) { animation-delay: 0.1s; }
            .feature-item:nth-child(2) { animation-delay: 0.2s; }
            .feature-item:nth-child(3) { animation-delay: 0.3s; }
            
            @keyframes fadeInUp {
                from {
                    opacity: 0;
                    transform: translateY(10px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }
            
            .feature-item i {
                font-size: 1.5rem;
                margin-right: 12px;
                background: rgba(255, 255, 255, 0.2);
                padding: 10px;
                border-radius: 50%;
                width: 40px;
                height: 40px;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            
            .feature-item span {
                font-size: 0.95rem;
            }
            
            .login-right {
                flex: 1;
                padding: 60px 40px;
                display: flex;
                flex-direction: column;
                justify-content: center;
            }
            
            .login-header {
                text-align: center;
                margin-bottom: 40px;
            }
            
            .login-header i {
                font-size: 3rem;
                color: #667eea;
                margin-bottom: 15px;
                animation: bounce 2s ease-in-out infinite;
            }
            
            @keyframes bounce {
                0%, 100% {
                    transform: translateY(0);
                }
                50% {
                    transform: translateY(-10px);
                }
            }
            
            .login-header h2 {
                color: #333;
                font-size: 1.8rem;
                font-weight: 700;
                margin-bottom: 10px;
            }
            
            .login-header p {
                color: #999;
                margin: 0;
            }
            
            .form-group {
                margin-bottom: 20px;
            }
            
            .form-label {
                color: #333;
                font-weight: 600;
                margin-bottom: 8px;
                display: block;
            }
            
            .form-control {
                border: 2px solid #e0e0e0;
                border-radius: 10px;
                padding: 12px 16px;
                font-size: 1rem;
                transition: all 0.3s ease;
                background: #f8f9fa;
            }
            
            .form-control:focus {
                border-color: #667eea;
                background: white;
                box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.15);
                outline: none;
            }
            
            .form-control::placeholder {
                color: #999;
            }
            
            .btn-login {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                border: none;
                color: white;
                padding: 12px 20px;
                border-radius: 10px;
                font-size: 1rem;
                font-weight: 600;
                margin-top: 10px;
                transition: all 0.3s ease;
                cursor: pointer;
            }
            
            .btn-login:hover {
                transform: translateY(-2px);
                box-shadow: 0 10px 25px rgba(102, 126, 234, 0.3);
                color: white;
            }
            
            .btn-login:active {
                transform: translateY(0);
            }
            
            .alert {
                border-radius: 10px;
                border: none;
                margin-bottom: 20px;
                animation: slideDown 0.3s ease-out;
            }
            
            @keyframes slideDown {
                from {
                    opacity: 0;
                    transform: translateY(-10px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }
            
            @media (max-width: 768px) {
                .login-container {
                    flex-direction: column;
                }
                
                .login-left {
                    padding: 40px 30px;
                }
                
                .login-right {
                    padding: 40px 30px;
                }
                
                .login-left h1 {
                    font-size: 2rem;
                }
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <!-- Left Panel -->
            <div class="login-left">
                <h1>🎓 Hệ Thống Quản Lý Sinh Viên</h1>
                <p>Nền tảng quản lý giáo dục toàn diện cho giáo viên và nhà quản lý.</p>
                
                <div class="feature-list">
                    <div class="feature-item">
                        <i class="bi bi-graph-up"></i>
                        <span><strong>Quản lý điểm số</strong> - Nhập, sửa và theo dõi điểm học sinh dễ dàng</span>
                    </div>
                    <div class="feature-item">
                        <i class="bi bi-people"></i>
                        <span><strong>Quản lý lớp học</strong> - Tạo lớp, phân công giáo viên hiệu quả</span>
                    </div>
                    <div class="feature-item">
                        <i class="bi bi-shield-check"></i>
                        <span><strong>Bảo mật cao</strong> - Hệ thống mã hóa, phân quyền toàn diện</span>
                    </div>
                </div>
            </div>
            
            <!-- Right Panel -->
            <div class="login-right">
                <div class="login-header">
                    <i class="bi bi-lock-fill"></i>
                    <h2>Đăng Nhập</h2>
                    <p>Vui lòng đăng nhập với tài khoản của bạn</p>
                </div>

                <% String error = (String) request.getAttribute("errorMessage");
                    if (error != null) {%>
                <div class="alert alert-danger" role="alert">
                    <i class="bi bi-exclamation-circle-fill me-2"></i><%= error%>
                </div>
                <% }%>

                <form action="${pageContext.request.contextPath}/auth?action=login" method="POST">
                    <div class="form-group">
                        <label for="username" class="form-label">
                            <i class="bi bi-person-circle me-2" style="color: #667eea;"></i>Tài khoản
                        </label>
                        <input type="text" class="form-control" id="username" name="username" 
                               placeholder="Nhập tài khoản của bạn" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="password" class="form-label">
                            <i class="bi bi-key-fill me-2" style="color: #667eea;"></i>Mật khẩu
                        </label>
                        <input type="password" class="form-control" id="password" name="password" 
                               placeholder="Nhập mật khẩu của bạn" required>
                    </div>
                    
                    <button type="submit" class="btn btn-login w-100">
                        <i class="bi bi-box-arrow-in-right me-2"></i>Đăng Nhập
                    </button>
                </form>
                
                <div style="text-align: center; margin-top: 20px; color: #999; font-size: 0.9rem;">
                    <p>Nếu quên mật khẩu, vui lòng liên hệ quản trị viên</p>
                </div>
            </div>
        </div>
    </body>
</html>