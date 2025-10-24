<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PropertEase - Feedback List</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #e8f5e9 0%, #c8e6c9 100%);
            min-height: 100vh;
            padding: 20px;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
        }

        .header {
            text-align: center;
            margin-bottom: 40px;
            color: #2e7d32;
        }

        .header h1 {
            font-size: 36px;
            margin-bottom: 10px;
        }

        .header p {
            font-size: 18px;
            opacity: 0.8;
        }

        .feedback-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
            gap: 25px;
            margin-bottom: 40px;
        }

        .feedback-card {
            background: white;
            border-radius: 15px;
            padding: 25px;
            box-shadow: 0 8px 25px rgba(76, 175, 80, 0.15);
            transition: all 0.3s ease;
            position: relative;
            border: 2px solid transparent;
        }

        .feedback-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 35px rgba(76, 175, 80, 0.25);
            border-color: #a5d6a7;
        }

        .card-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin-bottom: 15px;
        }

        .card-meta {
            flex: 1;
        }

        .role-badge {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            margin-bottom: 8px;
        }

        .role-buyer { background: #e3f2fd; color: #1976d2; }
        .role-tenant { background: #f3e5f5; color: #7b1fa2; }
        .role-vendor { background: #fff3e0; color: #f57c00; }
        .role-admin { background: #ffebee; color: #d32f2f; }
        .role-other { background: #f5f5f5; color: #616161; }

        .feedback-type {
            font-size: 14px;
            color: #666;
            font-weight: 500;
        }

        .card-actions {
            display: flex;
            gap: 8px;
        }

        .action-btn {
            width: 36px;
            height: 36px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 14px;
            transition: all 0.3s ease;
        }

        .edit-btn {
            background: linear-gradient(135deg, #42a5f5 0%, #1976d2 100%);
            color: white;
        }

        .edit-btn:hover {
            background: linear-gradient(135deg, #1976d2 0%, #1565c0 100%);
            transform: scale(1.1);
        }

        .delete-btn {
            background: linear-gradient(135deg, #ef5350 0%, #d32f2f 100%);
            color: white;
        }

        .delete-btn:hover {
            background: linear-gradient(135deg, #d32f2f 0%, #c62828 100%);
            transform: scale(1.1);
        }

        .card-subject {
            font-size: 18px;
            font-weight: 600;
            color: #2e7d32;
            margin-bottom: 12px;
            line-height: 1.4;
        }

        .card-description {
            color: #555;
            line-height: 1.6;
            margin-bottom: 15px;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }

        .card-footer {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-top: 15px;
            border-top: 1px solid #e8f5e9;
        }

        .rating {
            display: flex;
            gap: 2px;
        }

        .rating .star {
            color: #ffd700;
            font-size: 16px;
        }

        .rating .star.empty {
            color: #e0e0e0;
        }

        .feedback-date {
            font-size: 13px;
            color: #888;
        }

        .no-feedback {
            text-align: center;
            padding: 60px 20px;
            color: #666;
        }

        .no-feedback i {
            font-size: 64px;
            color: #a5d6a7;
            margin-bottom: 20px;
        }

        .no-feedback h3 {
            font-size: 24px;
            margin-bottom: 10px;
            color: #2e7d32;
        }

        .loading {
            text-align: center;
            padding: 60px 20px;
            color: #2e7d32;
        }

        .loading i {
            font-size: 48px;
            margin-bottom: 20px;
            animation: spin 1s linear infinite;
        }

        @keyframes spin {
            from { transform: rotate(0deg); }
            to { transform: rotate(360deg); }
        }

        /* Delete Confirmation Modal */
        .modal-overlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            justify-content: center;
            align-items: center;
            z-index: 1000;
            animation: fadeIn 0.3s ease;
        }

        .modal-overlay.show {
            display: flex;
        }

        .modal {
            background: white;
            padding: 30px;
            border-radius: 15px;
            text-align: center;
            max-width: 400px;
            width: 90%;
            animation: slideUp 0.3s ease;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
        }

        .modal-icon {
            width: 60px;
            height: 60px;
            background: linear-gradient(135deg, #ef5350 0%, #d32f2f 100%);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 20px;
            font-size: 24px;
            color: white;
        }

        .modal h3 {
            color: #d32f2f;
            margin-bottom: 10px;
            font-size: 20px;
        }

        .modal p {
            color: #666;
            margin-bottom: 25px;
            line-height: 1.5;
        }

        .modal-actions {
            display: flex;
            gap: 15px;
            justify-content: center;
        }

        .modal-btn {
            padding: 10px 20px;
            border: none;
            border-radius: 8px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            min-width: 80px;
        }

        .btn-cancel {
            background: #f5f5f5;
            color: #666;
        }

        .btn-cancel:hover {
            background: #e0e0e0;
        }

        .btn-delete {
            background: linear-gradient(135deg, #ef5350 0%, #d32f2f 100%);
            color: white;
        }

        .btn-delete:hover {
            background: linear-gradient(135deg, #d32f2f 0%, #c62828 100%);
        }

        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }

        @keyframes slideUp {
            from {
                transform: translateY(50px);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }

        @media (max-width: 768px) {
            .feedback-grid {
                grid-template-columns: 1fr;
                gap: 20px;
            }
            
            .header h1 {
                font-size: 28px;
            }
            
            .feedback-card {
                padding: 20px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>PropertEase Feedback</h1>
            <p>Manage and review all feedback submissions</p>
        </div>

        <div id="loadingContainer" class="loading">
            <i class="fa-solid fa-spinner"></i>
            <h3>Loading feedback...</h3>
        </div>

        <div id="feedbackContainer" class="feedback-grid" style="display: none;">
            <!-- Feedback cards will be dynamically loaded here -->
        </div>

        <div id="noFeedbackContainer" class="no-feedback" style="display: none;">
            <i class="fa-solid fa-comments"></i>
            <h3>No Feedback Found</h3>
            <p>There are no feedback submissions to display at the moment.</p>
        </div>
    </div>

    <!-- Delete Confirmation Modal -->
    <div class="modal-overlay" id="deleteModal">
        <div class="modal">
            <div class="modal-icon">
                <i class="fa-solid fa-trash"></i>
            </div>
            <h3>Delete Feedback</h3>
            <p>Are you sure you want to delete this feedback? This action cannot be undone.</p>
            <div class="modal-actions">
                <button class="modal-btn btn-cancel" onclick="closeDeleteModal()">Cancel</button>
                <button class="modal-btn btn-delete" onclick="confirmDelete()">Delete</button>
            </div>
        </div>
    </div>

    <script src="/js/feedback-list.js"></script>
</body>
</html>