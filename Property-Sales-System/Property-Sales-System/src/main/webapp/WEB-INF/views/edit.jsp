<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PropertEase - Edit Feedback</title>
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
            max-width: 700px;
            margin: 0 auto;
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(76, 175, 80, 0.2);
            overflow: hidden;
        }

        .header {
            background: linear-gradient(135deg, #66bb6a 0%, #43a047 100%);
            color: white;
            padding: 30px;
            text-align: center;
        }

        .header h1 {
            font-size: 32px;
            margin-bottom: 5px;
        }

        .header p {
            font-size: 16px;
            opacity: 0.95;
        }

        .form-container {
            padding: 40px;
        }

        .form-group {
            margin-bottom: 25px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #2e7d32;
            font-weight: 600;
            font-size: 14px;
        }

        .required {
            color: #d32f2f;
        }

        input[type="text"],
        select,
        textarea {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #a5d6a7;
            border-radius: 8px;
            font-size: 14px;
            font-family: inherit;
            transition: all 0.3s ease;
            background-color: #f1f8f4;
        }

        input[type="text"]:focus,
        select:focus,
        textarea:focus {
            outline: none;
            border-color: #66bb6a;
            background-color: white;
            box-shadow: 0 0 0 3px rgba(102, 187, 106, 0.1);
        }

        textarea {
            resize: vertical;
            min-height: 120px;
        }

        .star-rating {
            display: flex;
            gap: 10px;
            font-size: 32px;
            padding: 10px 0;
        }

        .star {
            cursor: pointer;
            color: #c8e6c9;
            transition: all 0.2s ease;
        }

        .star:hover,
        .star.active {
            color: #ffd700;
            transform: scale(1.1);
        }

        .checkbox-group {
            display: flex;
            align-items: flex-start;
            gap: 10px;
            padding: 15px;
            background-color: #f1f8f4;
            border-radius: 8px;
            border: 2px solid #a5d6a7;
        }

        .checkbox-group input[type="checkbox"] {
            margin-top: 3px;
            width: 18px;
            height: 18px;
            cursor: pointer;
            accent-color: #66bb6a;
        }

        .checkbox-group label {
            margin: 0;
            font-weight: normal;
            cursor: pointer;
            color: #424242;
        }

        .button-group {
            display: flex;
            gap: 15px;
            margin-top: 30px;
        }

        .submit-btn,
        .cancel-btn {
            flex: 1;
            padding: 15px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .submit-btn {
            background: linear-gradient(135deg, #66bb6a 0%, #43a047 100%);
            color: white;
        }

        .submit-btn:hover {
            background: linear-gradient(135deg, #43a047 0%, #2e7d32 100%);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(76, 175, 80, 0.3);
        }

        .cancel-btn {
            background: linear-gradient(135deg, #9e9e9e 0%, #757575 100%);
            color: white;
        }

        .cancel-btn:hover {
            background: linear-gradient(135deg, #757575 0%, #616161 100%);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(117, 117, 117, 0.3);
        }

        .submit-btn:active,
        .cancel-btn:active {
            transform: translateY(0);
        }

        .error-message {
            color: #d32f2f;
            font-size: 13px;
            margin-top: 5px;
            display: none;
        }

        .error-message.show {
            display: block;
        }

        input.error,
        select.error,
        textarea.error {
            border-color: #ef5350;
            background-color: #ffebee;
        }

        .loading {
            text-align: center;
            padding: 40px;
            color: #2e7d32;
            font-size: 18px;
        }

        .loading i {
            font-size: 32px;
            margin-bottom: 15px;
            animation: spin 1s linear infinite;
        }

        @keyframes spin {
            from {
                transform: rotate(0deg);
            }
            to {
                transform: rotate(360deg);
            }
        }

        .popup-overlay {
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

        .popup-overlay.show {
            display: flex;
        }

        .popup {
            background: white;
            padding: 40px;
            border-radius: 15px;
            text-align: center;
            max-width: 400px;
            animation: slideUp 0.3s ease;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
        }

        .popup-icon {
            width: 80px;
            height: 80px;
            background: linear-gradient(135deg, #66bb6a 0%, #43a047 100%);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 20px;
            font-size: 40px;
            color: white;
        }

        .popup h2 {
            color: #2e7d32;
            margin-bottom: 10px;
            font-size: 24px;
        }

        .popup p {
            color: #616161;
            margin-bottom: 25px;
            line-height: 1.6;
        }

        .popup-btn {
            padding: 12px 30px;
            background: linear-gradient(135deg, #66bb6a 0%, #43a047 100%);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .popup-btn:hover {
            background: linear-gradient(135deg, #43a047 0%, #2e7d32 100%);
        }

        .error-popup .popup-icon {
            background: linear-gradient(135deg, #ef5350 0%, #d32f2f 100%);
        }

        .error-popup h2 {
            color: #d32f2f;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
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
            .form-container {
                padding: 25px;
            }

            .header h1 {
                font-size: 24px;
            }

            .star-rating {
                font-size: 28px;
            }

            .button-group {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>PropertEase</h1>
        <p>Edit Your Feedback</p>
    </div>

    <div id="loadingContainer" class="loading">
        <i class="fa-solid fa-spinner"></i>
        <p>Loading feedback...</p>
    </div>

    <div class="form-container" id="formContainer" style="display: none;">
        <form id="editFeedbackForm">
            <input type="hidden" id="feedbackId" name="feedbackId">

            <div class="form-group">
                <label for="userRole">User Role <span class="required">*</span></label>
                <select id="userRole" name="userRole" required>
                    <option value="">Select your role</option>
                    <option value="buyer">Buyer</option>
                    <option value="tenant">Tenant</option>
                    <option value="vendor">Vendor</option>
                    <option value="admin">Admin</option>
                    <option value="other">Other</option>
                </select>
                <span class="error-message" id="roleError">Please select your role</span>
            </div>

            <div class="form-group">
                <label for="feedbackType">Feedback Type <span class="required">*</span></label>
                <select id="feedbackType" name="feedbackType" required>
                    <option value="">Select feedback type</option>
                    <option value="general">General Feedback</option>
                    <option value="property">Property Listing Issue</option>
                    <option value="technical">Technical/System Bug</option>
                    <option value="feature">Feature Request/Suggestion</option>
                    <option value="ux">User Experience Improvement</option>
                    <option value="payment">Payment Related</option>
                    <option value="other">Other</option>
                </select>
                <span class="error-message" id="typeError">Please select feedback type</span>
            </div>

            <div class="form-group">
                <label for="subject">Subject <span class="required">*</span></label>
                <input type="text" id="subject" name="subject" placeholder="Brief summary of your feedback" required>
                <span class="error-message" id="subjectError">Please enter a subject</span>
            </div>

            <div class="form-group">
                <label for="description">Comment/Description <span class="required">*</span></label>
                <textarea id="description" name="description" placeholder="Please provide detailed feedback..." required></textarea>
                <span class="error-message" id="descriptionError">Please enter your feedback details</span>
            </div>

            <div class="form-group">
                <label>Overall Rating <span class="required">*</span></label>
                <div class="star-rating" id="starRating">
                    <i class="fa-regular fa-star star" data-rating="1"></i>
                    <i class="fa-regular fa-star star" data-rating="2"></i>
                    <i class="fa-regular fa-star star" data-rating="3"></i>
                    <i class="fa-regular fa-star star" data-rating="4"></i>
                    <i class="fa-regular fa-star star" data-rating="5"></i>
                </div>
                <input type="hidden" id="rating" name="rating" value="0">
                <span class="error-message" id="ratingError">Please provide a rating</span>
            </div>

            <div class="form-group">
                <div class="checkbox-group">
                    <input type="checkbox" id="consent" name="consent" required>
                    <label for="consent">I agree to the collection and processing of this feedback. <span class="required">*</span></label>
                </div>
                <span class="error-message" id="consentError">You must agree to continue</span>
            </div>

            <div class="button-group">
                <button type="button" class="cancel-btn" onclick="cancelEdit()">Cancel</button>
                <button type="submit" class="submit-btn">Update Feedback</button>
            </div>
        </form>
    </div>
</div>

<div class="popup-overlay" id="successPopup">
    <div class="popup">
        <div class="popup-icon">
            <i class="fa-solid fa-check"></i>
        </div>
        <h2>Updated Successfully!</h2>
        <p>Your feedback has been updated successfully.</p>
        <button class="popup-btn" onclick="closeSuccessPopup()">Close</button>
    </div>
</div>

<div class="popup-overlay" id="errorPopup">
    <div class="popup error-popup">
        <div class="popup-icon">
            <i class="fa-solid fa-exclamation"></i>
        </div>
        <h2>Error</h2>
        <p id="errorMessage">Failed to load feedback. Please try again.</p>
        <button class="popup-btn" onclick="closeErrorPopup()">Close</button>
    </div>
</div>

<script src="/js/edit.js"></script>
</body>
</html>